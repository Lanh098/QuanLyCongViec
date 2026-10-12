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

        congViecGoc = intent.getCongViecExtra(
            CongViecKeys.EXTRA_TASK
        )

        if (congViecGoc == null) {
            Toast.makeText(
                this,
                "Không nhận được công việc cần sửa",
                Toast.LENGTH_SHORT
            ).show()

            finish()
            return
        }

        val etTen = findViewById<EditText>(
            R.id.etSuaTenCongViec
        )

        val etMoTa = findViewById<EditText>(
            R.id.etSuaMoTa
        )

        etTen.setText(
            congViecGoc?.tenCongViec.orEmpty()
        )

        etMoTa.setText(
            congViecGoc?.moTa.orEmpty()
        )

        // Lưu và trả lại đối tượng CongViec mới.
        findViewById<Button>(R.id.btnLuuCongViec)
            .setOnClickListener {

                val banDau = congViecGoc
                    ?: return@setOnClickListener

                val tenMoi = etTen.text.toString().trim()
                val moTaMoi = etMoTa.text.toString().trim()

                val congViecMoi = CongViec(
                    tenCongViec = tenMoi,
                    moTa = moTaMoi,
                    trangThai = banDau.trangThai
                )

                val ketQua = Intent().apply {
                    putExtra(
                        CongViecKeys.EXTRA_TASK,
                        congViecMoi
                    )
                }

                setResult(Activity.RESULT_OK, ketQua)
                finish()
            }

        // Hủy sửa.
        findViewById<Button>(R.id.btnHuySua)
            .setOnClickListener {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
    }
}