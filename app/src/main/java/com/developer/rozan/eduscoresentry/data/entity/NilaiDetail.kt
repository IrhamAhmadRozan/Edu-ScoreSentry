package com.developer.rozan.eduscoresentry.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NilaiDetail(
    var id_nilai : String,
    var nisn : String,
    val nama_siswa : String = "",
    var status_nilai : Int
) : Parcelable {

    companion object {
        const val TABLE_TX_NILAI_DETAIL = "tx_nilai_detail"

        const val ID_NILAI = "id_nilai"
        const val NISN = "nisn"
        const val NAMA_SISWA = "nama_siswa"
        const val STATUS_NILAI = "status_nilai"
    }

}
