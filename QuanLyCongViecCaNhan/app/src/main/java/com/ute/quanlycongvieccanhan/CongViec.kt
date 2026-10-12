package com.ute.quanlycongvieccanhan

import android.content.Intent
import android.os.Build
import java.io.Serializable

data class CongViec(
    var tenCongViec: String,
    var moTa: String,
    var trangThai: Boolean = false,
    var soLanChinhSua: Int = 0
) : Serializable {

    // Tạo chuỗi thông tin đầy đủ của công việc.
    fun thongTinCongViec(): String {

        val moTaHienThi = moTa.ifBlank {
            "Chưa có mô tả"
        }

        val trangThaiHienThi = if (trangThai) {
            "Đã hoàn thành"
        } else {
            "Chưa hoàn thành"
        }

        return """
            Tên công việc: $tenCongViec

            Mô tả:
            $moTaHienThi

            Trạng thái: $trangThaiHienThi

            Số lần chỉnh sửa: $soLanChinhSua
        """.trimIndent()
    }
}

// Các khóa dùng chung khi truyền dữ liệu bằng Intent.
object CongViecKeys {
    const val EXTRA_TASK = "extra_task"
    const val EXTRA_INDEX = "extra_index"
}

// Extension Function dùng để nhận đối tượng CongViec từ Intent.
@Suppress("DEPRECATION")
fun Intent.getCongViecExtra(key: String): CongViec? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializableExtra(key, CongViec::class.java)
    } else {
        getSerializableExtra(key) as? CongViec
    }
}
