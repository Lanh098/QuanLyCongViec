package com.ute.quanlycongvieccanhan

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SuaCongViecActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sua_cong_viec)

        val etTen = findViewById<EditText>(
            R.id.etSuaTenCongViec
        )

        val etMoTa = findViewById<EditText>(
            R.id.etSuaMoTa
        )

        // Điền dữ liệu cũ vào hai ô nhập.
        etTen.setText(
            intent.getStringExtra("tenCongViec").orEmpty()
        )

        etMoTa.setText(
            intent.getStringExtra("moTa").orEmpty()
        )

        // Trả dữ liệu mới về màn hình chi tiết.
        findViewById<Button>(R.id.btnLuuCongViec)
            .setOnClickListener {

                val tenMoi = etTen.text.toString().trim()
                val moTaMoi = etMoTa.text.toString().trim()

                val ketQua = Intent().apply {
                    putExtra("tenCongViec", tenMoi)
                    putExtra("moTa", moTaMoi)
                }

                setResult(Activity.RESULT_OK, ketQua)
                finish()
            }

        // Hủy sửa, không trả dữ liệu mới.
        findViewById<Button>(R.id.btnHuySua)
            .setOnClickListener {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
    }
}