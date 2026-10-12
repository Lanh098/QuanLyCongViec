package com.ute.quanlycongvieccanhan


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SuaCongViecActivity : AppCompatActivity() {

    private var congViecGoc: CongViec? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sua_cong_viec)

        // Nhận công việc cần chỉnh sửa.
        congViecGoc = intent.getCongViecExtra(
            CongViecKeys.EXTRA_TASK
        )

        if (congViecGoc == null) {
            Toast.makeText(
                this,
                "Không nhận được công việc cần sửa",
                Toast.LENGTH_SHORT
            ).show()

            setResult(Activity.RESULT_CANCELED)
            finish()
            return
        }

        val etTen = findViewById<EditText>(
            R.id.etSuaTenCongViec
        )

        val etMoTa = findViewById<EditText>(
            R.id.etSuaMoTa
        )

        // Hiển thị thông tin ban đầu.
        etTen.setText(
            congViecGoc?.tenCongViec.orEmpty()
        )

        etMoTa.setText(
            congViecGoc?.moTa.orEmpty()
        )

        // Nút Lưu.
        findViewById<Button>(R.id.btnLuuCongViec)
            .setOnClickListener {

                val banDau = congViecGoc
                    ?: return@setOnClickListener

                val tenMoi = etTen.text.toString().trim()
                val moTaMoi = etMoTa.text.toString().trim()

                // BƯỚC 1: Kiểm tra dữ liệu trước khi tăng bộ đếm.
                if (tenMoi.isBlank()) {
                    Toast.makeText(
                        this,
                        "Tên công việc không được để trống",
                        Toast.LENGTH_SHORT
                    ).show()

                    etTen.error = "Vui lòng nhập tên công việc"
                    etTen.requestFocus()

                    // Không lưu, không tăng số lần chỉnh sửa.
                    return@setOnClickListener
                }

                etTen.error = null

                // BƯỚC 2: Chỉ tạo bản cập nhật sau khi dữ liệu hợp lệ.
                // Số lần chỉnh sửa tăng đúng 1 khi lưu thành công.
                val congViecMoi = CongViec(
                    tenCongViec = tenMoi,
                    moTa = moTaMoi,
                    trangThai = banDau.trangThai,
                    soLanChinhSua = banDau.soLanChinhSua + 1
                )

                // BƯỚC 3: Trả kết quả thành công về màn hình chi tiết.
                val ketQua = Intent().apply {
                    putExtra(
                        CongViecKeys.EXTRA_TASK,
                        congViecMoi
                    )
                }

                setResult(Activity.RESULT_OK, ketQua)
                finish()
            }

        // Nút Hủy: không thay đổi dữ liệu và không tăng bộ đếm.
        findViewById<Button>(R.id.btnHuySua)
            .setOnClickListener {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
    }
}
