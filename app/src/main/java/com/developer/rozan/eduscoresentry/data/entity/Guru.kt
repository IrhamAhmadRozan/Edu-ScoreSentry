package com.developer.rozan.eduscoresentry.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Guru(
    val nip : String,
    val nama_guru : String,
    val email : String,
    val no_telp : String,
    val alamat : String,
    val password : String
) : Parcelable
