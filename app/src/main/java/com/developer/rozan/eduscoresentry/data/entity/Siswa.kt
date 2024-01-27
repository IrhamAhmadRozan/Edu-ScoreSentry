package com.developer.rozan.eduscoresentry.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Siswa(
    val nisn : String,
    val nama_siswa : String,
    val no_telp : String,
    val alamat : String,
    val password : String
) : Parcelable
