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
import com.developer.rozan.eduscoresentry.data.entity.KehadiranDetail
import com.developer.rozan.eduscoresentry.listener.OnStatusKehadiranUpdateClickListener

class DetailUpdateKehadiranAdapter(private val kehadiranDetailList: List<KehadiranDetail>) :
    RecyclerView.Adapter<DetailUpdateKehadiranAdapter.ViewHolder>() {

    var listener: OnStatusKehadiranUpdateClickListener? = null

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
        val kehDet = kehadiranDetailList[position]
        holder.tvNama.text = kehDet.nama_siswa
        holder.tvNISN.text = kehDet.nisn
        var spinpos: Int
        if (kehDet.status_kehadiran == "Hadir") {
            spinpos = 0
        } else if (kehDet.status_kehadiran == "Sakit") {
            spinpos = 1
        } else if (kehDet.status_kehadiran == "Izin") {
            spinpos = 2
        } else {
            spinpos = 3
        }
//        holder.spinnerKehadiran.setSelection(spinpos)

        val listItem = listOf("Hadir", "Sakit", "Izin", "Tanpa Keterangan")

        val spinnerArrayAdapter =
            ArrayAdapter(holder.itemView.context, android.R.layout.simple_spinner_item, listItem)
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.spinnerKehadiran.setAdapter(spinnerArrayAdapter)

        holder.spinnerKehadiran.setText(
            spinnerArrayAdapter.getItem(
                spinnerArrayAdapter.getPosition(
                    kehadiranDetailList[position].status_kehadiran
                )
            ).toString(), false
        );

        holder.spinnerKehadiran.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, pos, id ->
                val selectedItem = parent?.getItemAtPosition(pos).toString()
                kehadiranDetailList[position].status_kehadiran = selectedItem
                listener?.onItemClicked(view!!, kehadiranDetailList[position])
            }
    }

    override fun getItemCount(): Int {
        return kehadiranDetailList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.tv_nama)
        val tvNISN: TextView = view.findViewById(R.id.tv_nisn)
        val spinnerKehadiran: AutoCompleteTextView = view.findViewById(R.id.spinner_kehadiran)
    }
}