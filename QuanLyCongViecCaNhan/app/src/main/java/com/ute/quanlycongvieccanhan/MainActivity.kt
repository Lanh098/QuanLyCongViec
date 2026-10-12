package com.ute.quanlycongvieccanhan

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val danhSachCongViec = mutableListOf<Pair<String, String>>()

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

        findViewById<Button>(R.id.btnThemCongViec)
            .setOnClickListener {
                val ten = etTen.text.toString().trim()
                val moTa = etMoTa.text.toString().trim()

                danhSachCongViec.add(ten to moTa)

                etTen.text.clear()
                etMoTa.text.clear()

                if (llDanhSach.visibility == View.VISIBLE) {
                    hienThiDanhSach()
                }
            }

        findViewById<Button>(R.id.btnXemDanhSach)
            .setOnClickListener {
                val dangAn = llDanhSach.visibility != View.VISIBLE
                val visibility =
                    if (dangAn) View.VISIBLE else View.GONE

                llDanhSach.visibility = visibility
                tvTieuDeDanhSach.visibility = visibility

                if (dangAn) {
                    hienThiDanhSach()
                }
            }
    }

    private fun hienThiDanhSach() {
        llDanhSach.removeAllViews()

        if (danhSachCongViec.isEmpty()) {
            val tv = TextView(this).apply {
                text = "Chưa có công việc nào."
                textSize = 16f
            }
            llDanhSach.addView(tv)
            return
        }

        danhSachCongViec.forEachIndexed { index, congViec ->
            val tv = TextView(this).apply {
                text = "${index + 1}. ${congViec.first}"
                textSize = 16f
                setPadding(12, 16, 12, 16)
            }
            llDanhSach.addView(tv)
        }
    }
}
