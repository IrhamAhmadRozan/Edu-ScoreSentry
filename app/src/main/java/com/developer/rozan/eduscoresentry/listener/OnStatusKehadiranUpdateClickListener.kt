package com.developer.rozan.eduscoresentry.listener

import android.view.View
import com.developer.rozan.eduscoresentry.data.entity.KehadiranDetail
import com.developer.rozan.eduscoresentry.data.entity.KelasMember

interface OnStatusKehadiranUpdateClickListener {
    fun onItemClicked(view: View, kehadiranDetail: KehadiranDetail)
}