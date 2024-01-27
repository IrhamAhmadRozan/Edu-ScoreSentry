package com.developer.rozan.eduscoresentry.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Nilai(
    var id_nilai : String,
    var id_kelas : String,
    var judul_nilai : String,
    var nilai_detail : NilaiDetail? = null
) : Parcelable {
    companion object {
        const val NILAI = "Nilai"

        const val TABLE_TX_NILAI = "tx_nilai"

        const val ID_NILAI = "id_nilai"
        const val ID_KELAS = "id_kelas"
        const val JUDUL_NILAI = "judul_nilai"
    }
}
