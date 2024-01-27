package com.developer.rozan.eduscoresentry.view.upload_kehadiran.detail_upload_kehadiran

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.developer.rozan.eduscoresentry.R
import com.developer.rozan.eduscoresentry.data.entity.KelasMember
import com.developer.rozan.eduscoresentry.listener.OnStatusKehadiranInsertClickListener

class DetailUploadKehadiranAdapter(private val kelasMemberList: List<KelasMember>) :
    RecyclerView.Adapter<DetailUploadKehadiranAdapter.ViewHolder>() {

    var listener: OnStatusKehadiranInsertClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_upload_kehadiran_detail, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kelMem = kelasMemberList[position]
        holder.tvNama.text = kelMem.nama_siswa
        holder.tvNISN.text = kelMem.nisn

        holder.updatePosition(position)
    }

    override fun getItemCount(): Int {
        return kelasMemberList.size
    }

    inner class ViewHolder : RecyclerView.ViewHolder {

        val tvNama: TextView
        val tvNISN: TextView
        val spinnerKehadiran: AutoCompleteTextView

        private var pos = 0

        fun updatePosition(position: Int) {
            this.pos = position
        }

        constructor(view: View) : super(view) {
            tvNama = view.findViewById(R.id.tv_nama)
            tvNISN = view.findViewById(R.id.tv_nisn)
            spinnerKehadiran = view.findViewById(R.id.spinner_kehadiran)

            val listItem = listOf("Hadir", "Sakit", "Izin", "Tanpa Keterangan")

            val spinnerArrayAdapter =
                ArrayAdapter(view.context, android.R.layout.simple_spinner_item, listItem)
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerKehadiran.setAdapter(spinnerArrayAdapter)

            spinnerKehadiran.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    val selectedItem = parent?.getItemAtPosition(position).toString()
                    kelasMemberList[pos].kehadiran_temp = selectedItem
                    listener?.onItemClicked(view!!, kelasMemberList[pos])
                }
        }
    }
}