package com.developer.rozan.eduscoresentry.view.lihat_kehadiran

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.developer.rozan.eduscoresentry.R
import com.developer.rozan.eduscoresentry.data.entity.Kehadiran

class LihatKehadiranAdapter(private val kehadiranList: List<Kehadiran>) : RecyclerView.Adapter<LihatKehadiranAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_lihat, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val keh = kehadiranList[position]
        holder.tvJudul.text = keh.judul_kehadiran
        holder.tvStatus.text = "Status Kehadiran : " + keh.kehadiran_detail!!.status_kehadiran
    }

    override fun getItemCount(): Int {
        return kehadiranList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvJudul: TextView = view.findViewById(R.id.tv_judul)
        val tvStatus: TextView = view.findViewById(R.id.tv_status)
    }
}