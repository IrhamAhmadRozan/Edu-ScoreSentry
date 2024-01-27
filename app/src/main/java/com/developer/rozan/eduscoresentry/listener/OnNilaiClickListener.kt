package com.developer.rozan.eduscoresentry.listener

import android.view.View
import com.developer.rozan.eduscoresentry.data.entity.Nilai

interface OnNilaiClickListener {
    fun onItemClicked(view: View, nilai: Nilai)
}