package com.developer.rozan.eduscoresentry.listener

import android.view.View
import com.developer.rozan.eduscoresentry.data.entity.Kelas

interface OnKelasClickListener {
    fun onItemClicked(view: View, kelas: Kelas)
}