package com.ute.quanlycongvieccanhan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class ChiTietCongViecActivity : AppCompatActivity() {

    private var tenCongViec: String = ""
    private var moTa: String = ""

    // Nhận kết quả sau khi sửa công việc.
    private val suaLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == RESULT_OK) {
                val data = result.data

                if (data != null) {
                    tenCongViec =
                        data.getStringExtra("tenCongViec")
                            ?: tenCongViec

                    moTa =
                        data.getStringExtra("moTa")
                            ?: moTa

                    hienThiThongTin()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chi_tiet_cong_viec)

        // Nhận dữ liệu được truyền từ MainActivity.
        tenCongViec =
            intent.getStringExtra("tenCongViec").orEmpty()

        moTa =
            intent.getStringExtra("moTa").orEmpty()

        hienThiThongTin()

        // Mở màn hình sửa.
        findViewById<Button>(R.id.btnSuaCongViec)
            .setOnClickListener {

                val intentSua = Intent(
                    this,
                    SuaCongViecActivity::class.java
                ).apply {
                    putExtra("tenCongViec", tenCongViec)
                    putExtra("moTa", moTa)
                }

                suaLauncher.launch(intentSua)
            }

        // Quay lại màn hình chính.
        findViewById<Button>(R.id.btnQuayLai)
            .setOnClickListener {
                finish()
            }
    }

    private fun hienThiThongTin() {
        val tvThongTin =
            findViewById<TextView>(R.id.tvThongTinChiTiet)

        tvThongTin.text =
            "Tên công việc: $tenCongViec\n\n" +
                    "Mô tả công việc:\n$moTa"
    }
}
