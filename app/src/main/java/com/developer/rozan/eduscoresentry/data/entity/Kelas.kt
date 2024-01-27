package com.developer.rozan.eduscoresentry.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Kelas(
    val id_kelas : String,
    val id_mata_pelajaran : String,
    val nama_mata_pelajaran : String,
    val nip : String,
    val nama_kelas : String,
    val jadwal_kelas : String,
    val kelas_member: KelasMember? = null
) : Parcelable {
    companion object {
        const val KELAS = "Kelas"

        const val ID_KELAS = "id_kelas"
        const val ID_MATA_PELAJARAN = "id_mata_pelajaran"
        const val NAMA_MATA_PELAJARAN = "nama_mata_pelajaran"
        const val NIP = "nip"
        const val NAMA_KELAS = "nama_kelas"
        const val JADWAL_KELAS = "jadwal_kelas"
    }
}
