package com.ute.quanlycongvieccanhan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class ChiTietCongViecActivity : AppCompatActivity() {

    private var congViec: CongViec? = null
    private var viTri: Int = -1

    // Nhận model đã sửa từ SuaCongViecActivity.
    private val suaLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == RESULT_OK) {

                val congViecMoi =
                    result.data?.getCongViecExtra(
                        CongViecKeys.EXTRA_TASK
                    )

                if (congViecMoi != null) {
                    congViec = congViecMoi
                    hienThiThongTin()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chi_tiet_cong_viec)

        congViec = intent.getCongViecExtra(
            CongViecKeys.EXTRA_TASK
        )

        viTri = intent.getIntExtra(
            CongViecKeys.EXTRA_INDEX,
            -1
        )

        if (congViec == null) {
            Toast.makeText(
                this,
                "Không nhận được thông tin công việc",
                Toast.LENGTH_SHORT
            ).show()

            finish()
            return
        }

        hienThiThongTin()

        // Mở màn hình sửa.
        findViewById<Button>(R.id.btnSuaCongViec)
            .setOnClickListener {

                val hienTai = congViec ?: return@setOnClickListener

                val intentSua = Intent(
                    this,
                    SuaCongViecActivity::class.java
                ).apply {
                    putExtra(
                        CongViecKeys.EXTRA_TASK,
                        hienTai
                    )
                }

                suaLauncher.launch(intentSua)
            }

        // Nút quay lại.
        findViewById<Button>(R.id.btnQuayLai)
            .setOnClickListener {
                traVeManHinhChinh()
            }

        // Xử lý phím Back của thiết bị.
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    traVeManHinhChinh()
                }
            }
        )
    }

    private fun hienThiThongTin() {
        val hienTai = congViec ?: return

        findViewById<TextView>(R.id.tvThongTinChiTiet).text =
            hienTai.thongTinCongViec()
    }

    // Trả model và vị trí về MainActivity.
    private fun traVeManHinhChinh() {

        val hienTai = congViec

        if (hienTai == null) {
            setResult(RESULT_CANCELED)
            finish()
            return
        }

        val ketQua = Intent().apply {
            putExtra(
                CongViecKeys.EXTRA_TASK,
                hienTai
            )

            putExtra(
                CongViecKeys.EXTRA_INDEX,
                viTri
            )
        }

        setResult(RESULT_OK, ketQua)
        finish()
    }
}
