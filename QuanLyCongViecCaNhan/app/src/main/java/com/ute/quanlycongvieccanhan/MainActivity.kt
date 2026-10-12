package com.ute.quanlycongvieccanhan
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val danhSachCongViec = mutableListOf<CongViec>()

    private lateinit var etTen: EditText
    private lateinit var etMoTa: EditText
    private lateinit var llDanhSach: LinearLayout
    private lateinit var tvTieuDeDanhSach: TextView

    // Nhận công việc đã cập nhật từ màn hình chi tiết.
    private val chiTietLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == RESULT_OK) {
                val data = result.data

                if (data != null) {
                    val index = data.getIntExtra(
                        CongViecKeys.EXTRA_INDEX,
                        -1
                    )

                    val congViecMoi =
                        data.getCongViecExtra(
                            CongViecKeys.EXTRA_TASK
                        )

                    if (
                        congViecMoi != null &&
                        index in danhSachCongViec.indices
                    ) {
                        danhSachCongViec[index] = congViecMoi

                        if (
                            llDanhSach.visibility == View.VISIBLE
                        ) {
                            hienThiDanhSach()
                        }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etTen = findViewById(R.id.etTenCongViec)
        etMoTa = findViewById(R.id.etMoTa)
        llDanhSach = findViewById(R.id.llDanhSachCongViec)
        tvTieuDeDanhSach = findViewById(R.id.tvTieuDeDanhSach)

        // Thêm công việc mới.
        findViewById<Button>(R.id.btnThemCongViec)
            .setOnClickListener {

                val ten = etTen.text.toString().trim()
                val moTa = etMoTa.text.toString().trim()

                // Không cho phép tên công việc rỗng.
                if (ten.isBlank()) {
                    Toast.makeText(
                        this,
                        "Tên công việc không được để trống",
                        Toast.LENGTH_SHORT
                    ).show()

                    etTen.error = "Vui lòng nhập tên công việc"
                    etTen.requestFocus()
                    return@setOnClickListener
                }

                etTen.error = null

                val congViec = CongViec(
                    tenCongViec = ten,
                    moTa = moTa
                )

                danhSachCongViec.add(congViec)

                etTen.text.clear()
                etMoTa.text.clear()

                Toast.makeText(
                    this,
                    "Đã thêm công việc",
                    Toast.LENGTH_SHORT
                ).show()

                if (
                    llDanhSach.visibility == View.VISIBLE
                ) {
                    hienThiDanhSach()
                }

                // Mở màn hình chi tiết bằng Activity Result API.
                moChiTietCongViec(
                    danhSachCongViec.lastIndex
                )
            }

        // Hiện hoặc ẩn danh sách.
        findViewById<Button>(R.id.btnXemDanhSach)
            .setOnClickListener {

                val dangAn =
                    llDanhSach.visibility != View.VISIBLE

                val visibility = if (dangAn) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

                llDanhSach.visibility = visibility
                tvTieuDeDanhSach.visibility = visibility

                if (dangAn) {
                    hienThiDanhSach()
                }
            }
    }

    // Mở màn hình chi tiết và truyền model.
    private fun moChiTietCongViec(index: Int) {

        if (index !in danhSachCongViec.indices) {
            return
        }

        val congViec = danhSachCongViec[index]

        val intent = Intent(
            this,
            ChiTietCongViecActivity::class.java
        ).apply {
            putExtra(
                CongViecKeys.EXTRA_TASK,
                congViec
            )

            putExtra(
                CongViecKeys.EXTRA_INDEX,
                index
            )
        }

        chiTietLauncher.launch(intent)
    }

    // Hiển thị danh sách kèm trạng thái.
    private fun hienThiDanhSach() {

        llDanhSach.removeAllViews()

        if (danhSachCongViec.isEmpty()) {
            llDanhSach.addView(
                TextView(this).apply {
                    text = "Chưa có công việc nào."
                    textSize = 16f
                    setPadding(12, 16, 12, 16)
                }
            )
            return
        }

        danhSachCongViec.forEachIndexed { index, congViec ->

            val trangThai = if (congViec.trangThai) {
                "Đã hoàn thành"
            } else {
                "Chưa hoàn thành"
            }

            val btn = Button(this).apply {
                text = "${index + 1}. ${congViec.tenCongViec}\n$trangThai"
                gravity = Gravity.START or Gravity.CENTER_VERTICAL

                setOnClickListener {
                    moChiTietCongViec(index)
                }
            }

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            params.bottomMargin = 8

            llDanhSach.addView(btn, params)
        }
    }
}