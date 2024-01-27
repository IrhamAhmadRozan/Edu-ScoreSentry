package com.developer.rozan.eduscoresentry.listener

import android.view.View
import com.developer.rozan.eduscoresentry.data.entity.Kehadiran

interface OnKehadiranClickListener {
    fun onItemClicked(view: View, kehadiran: Kehadiran)
}