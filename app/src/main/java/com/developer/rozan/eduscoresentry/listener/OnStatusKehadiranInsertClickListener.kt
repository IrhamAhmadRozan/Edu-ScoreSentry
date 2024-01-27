package com.developer.rozan.eduscoresentry.listener

import android.view.View
import com.developer.rozan.eduscoresentry.data.entity.KelasMember

interface OnStatusKehadiranInsertClickListener {
    fun onItemClicked(view: View, kelasMember: KelasMember)
}