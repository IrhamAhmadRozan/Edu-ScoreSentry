package com.developer.rozan.eduscoresentry.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Kehadiran(
    var id_kehadiran : String,
    var id_kelas : String,
    var judul_kehadiran : String,
    var kehadiran_detail : KehadiranDetail? = null
) : Parcelable {
    companion object {
        const val KEHADIRAN = "Kehadiran"

        const val TABLE_TX_KEHADIRAN = "tx_kehadiran"

        const val ID_KEHADIRAN = "id_kehadiran"
        const val ID_KELAS = "id_kelas"
        const val JUDUL_KEHADIRAN = "judul_kehadiran"
    }
}
