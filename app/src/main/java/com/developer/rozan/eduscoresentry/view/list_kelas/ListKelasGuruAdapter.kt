package com.developer.rozan.eduscoresentry.view.list_kelas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.developer.rozan.eduscoresentry.R
import com.developer.rozan.eduscoresentry.data.entity.Kelas
import com.developer.rozan.eduscoresentry.listener.OnKelasClickListener

class ListKelasGuruAdapter(private val kelas: List<Kelas>) : RecyclerView.Adapter<ListKelasGuruAdapter.ViewHolder>() {

    var listener: OnKelasClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_kelas, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kel = kelas[position]
        holder.tvNamaPelajaran.text = kel.nama_mata_pelajaran
        holder.tvNamaKelas.text = kel.nama_kelas
        holder.tvJadwalKelas.text = kel.jadwal_kelas

        holder.cvKelas.setOnClickListener {
            listener?.onItemClicked(it, kel)
        }
    }

    override fun getItemCount(): Int {
        return kelas.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvKelas: CardView = view.findViewById(R.id.cv_kelas)
        val tvNamaPelajaran: TextView = view.findViewById(R.id.tv_nama_pelajaran)
        val tvNamaKelas: TextView = view.findViewById(R.id.tv_nama_kelas)
        val tvJadwalKelas: TextView = view.findViewById(R.id.tv_jadwal_kelas)
    }
}