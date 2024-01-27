package com.developer.rozan.eduscoresentry.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MataPelajaran(
    val id_mata_pelajaran : String,
    val nama_mata_pelajaran : String
) : Parcelable
