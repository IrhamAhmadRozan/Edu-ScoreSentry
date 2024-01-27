package com.developer.rozan.eduscoresentry.view.lihat_nilai

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.developer.rozan.eduscoresentry.R
import com.developer.rozan.eduscoresentry.data.entity.Nilai

class LihatNilaiAdapter(private val nilaiList: List<Nilai>) : RecyclerView.Adapter<LihatNilaiAdapter.ViewHolder>() {

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
        val nil = nilaiList[position]
        holder.tvJudul.text = nil.judul_nilai
        holder.tvStatus.text = "Nilai : " + nil.nilai_detail!!.status_nilai
    }

    override fun getItemCount(): Int {
        return nilaiList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvJudul: TextView = view.findViewById(R.id.tv_judul)
        val tvStatus: TextView = view.findViewById(R.id.tv_status)
    }
}