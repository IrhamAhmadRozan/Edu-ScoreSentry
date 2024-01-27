package com.developer.rozan.eduscoresentry.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KelasMember(
    val id_kelas : String,
    val nisn : String,
    val nama_siswa : String = "",
    var nilai_temp : Int = 0,
    var kehadiran_temp : String = ""
) : Parcelable {
    companion object {
        const val ID_KELAS = "id_kelas"
        const val NISN = "nisn"
        const val NAMA_SISWA = "nama_siswa"
    }
}
