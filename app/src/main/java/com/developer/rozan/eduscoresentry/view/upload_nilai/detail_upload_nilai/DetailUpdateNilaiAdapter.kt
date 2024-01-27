package com.developer.rozan.eduscoresentry.view.upload_nilai.detail_upload_nilai

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.developer.rozan.eduscoresentry.R
import com.developer.rozan.eduscoresentry.data.entity.NilaiDetail

class DetailUpdateNilaiAdapter(private val nilaiDetailList: List<NilaiDetail>) :
    RecyclerView.Adapter<DetailUpdateNilaiAdapter.ViewHolder>() {

    private var onUpdateNilaiUpdateEditText: OnUpdateNilaiUpdateEditTextListener? = null
    private var score = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_upload_nilai_detail, parent, false),
            CustomETListener()
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lapDet = nilaiDetailList[position]
        holder.tvNama.text = lapDet.nama_siswa
        holder.tvNISN.text = lapDet.nisn

        holder.edtNilai.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                holder.edtNilai.removeTextChangedListener(this)
                if (editable.isNotEmpty()) {
                    holder.edtNilai.error = null
                    if (editable.toString().contains("0") && editable.length > 1) {
                        val s = editable.toString()
                        if (!s.substring(0, 1).matches("[1-9]".toRegex())) {
                            holder.edtNilai.hint = "0 - 100"
                            score = 0
                        }
                    } else {
                        score = 0
                    }
                } else {
                    holder.edtNilai.hint = "0 - 100"
                    score = 0
                }
                val givenstring =
                    editable.toString().replace(",".toRegex(), "").replace("\\.".toRegex(), "")
                try {
                    score = givenstring.toInt()
                    if (score > 100) {
                        holder.edtNilai.error = "Maksimal angka adalah 100"
                        score = 100
                    }
                    holder.edtNilai.setText(score.toString())
                } catch (nfe: NumberFormatException) {
                    nfe.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                holder.edtNilai.setSelection(holder.edtNilai.text.length)
                holder.edtNilai.addTextChangedListener(this)
            }
        })

        holder.customETListener!!.updatePosition(position)
        if (lapDet.status_nilai == 0) {
            holder.edtNilai.hint = "0 - 100"
        } else {
            holder.edtNilai.setText(lapDet.status_nilai.toString())
            holder.edtNilai.setSelection(lapDet.status_nilai.toString().length)
        }
    }

    override fun getItemCount(): Int {
        return nilaiDetailList.size
    }

    class ViewHolder : RecyclerView.ViewHolder {
        val tvNama: TextView
        val tvNISN: TextView
        val edtNilai: EditText
        var customETListener: CustomETListener? = null

        constructor(view: View, customETListener: CustomETListener) : super(view) {
            tvNama = view.findViewById(R.id.tv_nama)
            tvNISN = view.findViewById(R.id.tv_nisn)
            edtNilai = view.findViewById(R.id.edt_nilai)

            this.customETListener = customETListener
            edtNilai.addTextChangedListener(customETListener)
        }
    }

    interface OnUpdateNilaiUpdateEditTextListener {
        fun onUpdateNilaiUpdateEditText()
    }

    fun setUpdateNilaiEditTextUpdate(onUpdateNilaiUpdateProductListener: OnUpdateNilaiUpdateEditTextListener) {
        onUpdateNilaiUpdateEditText = onUpdateNilaiUpdateProductListener
    }

    inner class CustomETListener : TextWatcher {

        private var position = 0

        fun updatePosition(position: Int) {
            this.position = position
        }

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            // todo nothing
        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            // todo nothing
        }

        override fun afterTextChanged(editable: Editable) {
            if (editable.toString() == "0" || editable.toString().isEmpty()) {
                nilaiDetailList[position].status_nilai = 0
            } else {
                nilaiDetailList[position].status_nilai = score
            }
            if (onUpdateNilaiUpdateEditText != null) {
                onUpdateNilaiUpdateEditText!!.onUpdateNilaiUpdateEditText()
            }
        }
    }
}