package com.developer.rozan.eduscoresentry.view.upload_nilai

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.developer.rozan.eduscoresentry.R
import com.developer.rozan.eduscoresentry.data.entity.Nilai
import com.developer.rozan.eduscoresentry.listener.OnNilaiClickListener

class UploadNilaiAdapter(private val nilaiList: List<Nilai>) : RecyclerView.Adapter<UploadNilaiAdapter.ViewHolder>() {

    var listener: OnNilaiClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_upload_nilai, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nil = nilaiList[position]
        holder.tvDeskripsiNilai.text = nil.judul_nilai

        holder.cvListNilai.setOnClickListener {
            listener?.onItemClicked(it, nil)
        }
    }

    override fun getItemCount(): Int {
        return nilaiList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvListNilai: CardView = view.findViewById(R.id.cv_list_nilai)
        val tvDeskripsiNilai: TextView = view.findViewById(R.id.tv_deskripsi_nilai)
    }
}