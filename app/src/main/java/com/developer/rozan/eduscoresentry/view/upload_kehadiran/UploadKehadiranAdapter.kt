package com.developer.rozan.eduscoresentry.view.upload_kehadiran

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.developer.rozan.eduscoresentry.R
import com.developer.rozan.eduscoresentry.data.entity.Kehadiran
import com.developer.rozan.eduscoresentry.listener.OnKehadiranClickListener

class UploadKehadiranAdapter(private val kehadiranList: List<Kehadiran>) :
    RecyclerView.Adapter<UploadKehadiranAdapter.ViewHolder>() {

    var listener: OnKehadiranClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_upload_nilai, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val keh = kehadiranList[position]
        holder.tvDeskripsiKehadiran.text = keh.judul_kehadiran

        holder.cvListKehadiran.setOnClickListener {
            listener?.onItemClicked(it, keh)
        }
    }

    override fun getItemCount(): Int {
        return kehadiranList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvListKehadiran: CardView = view.findViewById(R.id.cv_list_nilai)
        val tvDeskripsiKehadiran: TextView = view.findViewById(R.id.tv_deskripsi_nilai)
    }
}