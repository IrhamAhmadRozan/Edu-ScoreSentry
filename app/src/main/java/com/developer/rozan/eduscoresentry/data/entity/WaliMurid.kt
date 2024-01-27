package com.developer.rozan.eduscoresentry.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WaliMurid(
    val id_wali_murid : String,
    val nama_wali_murid : String,
    val no_telp : String,
    val alamat : String,
    val email : String,
    val nisn : String,
    val password : String
) : Parcelable
