package com.developer.rozan.eduscoresentry.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KehadiranDetail(
    var id_kehadiran : String,
    var nisn : String,
    val nama_siswa : String = "",
    var status_kehadiran : String,
) : Parcelable {

    companion object {
        const val TABLE_TX_KEHADIRAN_DETAIL = "tx_kehadiran_detail"

        const val ID_KEHADIRAN = "id_kehadiran"
        const val NISN = "nisn"
        const val NAMA_SISWA = "nama_siswa"
        const val STATUS_KEHADIRAN = "status_kehadiran"
    }

}
