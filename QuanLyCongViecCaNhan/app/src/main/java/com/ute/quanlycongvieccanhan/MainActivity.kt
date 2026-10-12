package com.ute.quanlycongvieccanhan

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val danhSachCongViec =
        mutableListOf<Pair<String, String>>()

    private lateinit var etTen: EditText
    private lateinit var etMoTa: EditText
    private lateinit var llDanhSach: LinearLayout
    private lateinit var tvTieuDeDanhSach: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etTen = findViewById(R.id.etTenCongViec)
        etMoTa = findViewById(R.id.etMoTa)
        llDanhSach = findViewById(R.id.llDanhSachCongViec)
        tvTieuDeDanhSach = findViewById(R.id.tvTieuDeDanhSach)

        // Nút Tạo công việc.
        findViewById<Button>(R.id.btnThemCongViec)
            .setOnClickListener {

                val ten = etTen.text.toString().trim()
                val moTa = etMoTa.text.toString().trim()

                // Lưu công việc vào danh sách tạm.
                danhSachCongViec.add(ten to moTa)

                // Xóa nội dung ô nhập sau khi tạo.
                etTen.text.clear()
                etMoTa.text.clear()

                // Cập nhật danh sách nếu danh sách đang mở.
                if (llDanhSach.visibility == View.VISIBLE) {
                    hienThiDanhSach()
                }

                // Mở màn hình chi tiết bằng Intent.
                moChiTietCongViec(ten, moTa)
            }

        // Nút hiển thị hoặc ẩn danh sách công việc.
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

    // Truyền tên và mô tả sang ChiTietCongViecActivity.
    private fun moChiTietCongViec(
        ten: String,
        moTa: String
    ) {
        val intent = Intent(
            this,
            ChiTietCongViecActivity::class.java
        )

        intent.putExtra("tenCongViec", ten)
        intent.putExtra("moTa", moTa)

        startActivity(intent)
    }

    // Hiển thị danh sách công việc trên màn hình chính.
    private fun hienThiDanhSach() {
        llDanhSach.removeAllViews()

        if (danhSachCongViec.isEmpty()) {
            val tv = TextView(this).apply {
                text = "Chưa có công việc nào."
                textSize = 16f
                setPadding(12, 16, 12, 16)
            }

            llDanhSach.addView(tv)
            return
        }

        danhSachCongViec.forEachIndexed { index, congViec ->

            val btn = Button(this).apply {
                text = "${index + 1}. ${congViec.first}"

                setOnClickListener {
                    moChiTietCongViec(
                        congViec.first,
                        congViec.second
                    )
                }
            }

            llDanhSach.addView(btn)
        }
    }
}
