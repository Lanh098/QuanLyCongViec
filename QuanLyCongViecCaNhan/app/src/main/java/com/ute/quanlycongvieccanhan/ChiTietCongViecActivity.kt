package com.ute.quanlycongvieccanhan

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ChiTietCongViecActivity : AppCompatActivity() {

    private var tenCongViec: String = ""
    private var moTa: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chi_tiet_cong_viec)
        tenCongViec =
            intent.getStringExtra("tenCongViec").orEmpty()
        moTa =
            intent.getStringExtra("moTa").orEmpty()
        hienThiThongTin()
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
