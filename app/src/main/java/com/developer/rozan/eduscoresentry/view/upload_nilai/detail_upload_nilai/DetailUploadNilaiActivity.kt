package com.developer.rozan.eduscoresentry.view.upload_nilai.detail_upload_nilai

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.rozan.eduscoresentry.data.entity.Kelas
import com.developer.rozan.eduscoresentry.data.entity.KelasMember
import com.developer.rozan.eduscoresentry.data.entity.Nilai
import com.developer.rozan.eduscoresentry.data.entity.NilaiDetail
import com.developer.rozan.eduscoresentry.databinding.ActivityDetailUploadNilaiBinding
import com.developer.rozan.eduscoresentry.util.constant.Constant
import com.developer.rozan.eduscoresentry.util.extension.gone
import com.developer.rozan.eduscoresentry.util.extension.visible
import com.developer.rozan.eduscoresentry.util.helper.DatabaseHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailUploadNilaiActivity : AppCompatActivity(),
    DetailUploadNilaiAdapter.OnUploadNilaiUpdateEditTextListener,
    DetailUpdateNilaiAdapter.OnUpdateNilaiUpdateEditTextListener {

    private lateinit var binding: ActivityDetailUploadNilaiBinding
    private lateinit var databaseHelper: DatabaseHelper

    private var kelas: Kelas? = null
    private var nilai: Nilai? = null
    private var action: String = ""

    private var kelasMemberList: List<KelasMember> = mutableListOf()
    private var nilaiDetailList: List<NilaiDetail> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUploadNilaiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(applicationContext)

        if (intent != null) {
            kelas = intent.getParcelableExtra(Kelas.KELAS)
            nilai = intent.getParcelableExtra(Nilai.NILAI)
            action = intent.getStringExtra("action").toString()
        }

        binding.toolbarBack.layoutAction2.visible()

        binding.toolbarBack.layoutBack.setOnClickListener {
            finish()
        }

        binding.toolbarBack.layoutAction2.setOnClickListener {
            if (action == "add") {
                setUpInsertNilai()
            } else {
                setUpUpdateNilai()
            }
        }

        binding.emptyLayout.btnTryAgain.setOnClickListener {
            initFirst()
        }

        initFirst()
    }

    private fun initFirst() {
        if (action == "add") {
            binding.toolbarBack.titleToolbarBack.text = "Tambah Nilai"
            kelasMemberList = databaseHelper.getListKelasMemberByIdKelas(kelas!!.id_kelas)
            if (kelasMemberList.isNotEmpty()) {
                lifecycleScope.launch {
                    delay(Constant.DELAY_INTENT)
                    binding.rvDetailNilai.visible()
                    binding.emptyLayout.clEmpty.gone()
                    setUpNilai(action)
                }
            } else {
                emptyLayoutMTD("Tidak ada siswa.")
            }
        } else {
            binding.toolbarBack.titleToolbarBack.text = "Ubah Nilai"
            nilaiDetailList = databaseHelper.getListNilaiDetailByIdNilai(nilai!!.id_nilai)
            if (nilaiDetailList.isNotEmpty()) {
                lifecycleScope.launch {
                    delay(Constant.DELAY_INTENT)
                    binding.rvDetailNilai.visible()
                    binding.emptyLayout.clEmpty.gone()
                    setUpNilai(action)
                }
            } else {
                emptyLayoutMTD("Tidak ada siswa.")
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpNilai(action: String) {
        binding.rvDetailNilai.layoutManager = LinearLayoutManager(this)
        if (action == "add") {
            var adapter = DetailUploadNilaiAdapter(kelasMemberList)
            adapter.setUploadNilaiEditTextUpdate(this)
            binding.rvDetailNilai.adapter = adapter
            adapter.notifyDataSetChanged()
        } else {
            binding.tfDetailJudulNilai.setText(nilai!!.judul_nilai)
            var adapter = DetailUpdateNilaiAdapter(nilaiDetailList)
            adapter.setUpdateNilaiEditTextUpdate(this)
            binding.rvDetailNilai.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("NewApi")
    private fun setUpInsertNilai() {
        val judulNilai = binding.tfDetailJudulNilai.text.toString()

        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        val current = LocalDateTime.now().format(formatter)

        if (judulNilai.isNotEmpty()) {
            val idFormatter = "Nilai-" + kelas?.id_kelas + "-" + kelas?.nip + "-" + current
            val nilai = Nilai(idFormatter, kelas?.id_kelas!!, judulNilai)
            databaseHelper.insertTxNilai(nilai)

            for (i in kelasMemberList.indices) {
                val nilaiDetail = NilaiDetail(
                    nilai.id_nilai,
                    kelasMemberList[i].nisn,
                    "",
                    kelasMemberList[i].nilai_temp
                )

                val resultInsert = databaseHelper.insertTxNilaiDetail(nilaiDetail)
                if (i + 1 == kelasMemberList.size) {
                    if (resultInsert.toInt() != -1) {
                        onInsertSuccess(i + 1)
                    } else {
                        onInsertFailure()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Judul Nilai wajib diisi.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onInsertSuccess(row: Int) {
        Toast.makeText(
            this,
            "Berhasil memasukkan $row data.",
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }

    private fun onInsertFailure() {
        Toast.makeText(
            this,
            "Gagal memasukkan data.",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setUpUpdateNilai() {
        val judulNilai = binding.tfDetailJudulNilai.text.toString()
        val updateNilai = Nilai(nilai!!.id_nilai, nilai!!.id_kelas, judulNilai)
        databaseHelper.updateTxNilai(updateNilai)

        if (judulNilai.isNotEmpty()) {
            for (i in nilaiDetailList.indices) {
                val nilaiDetail = NilaiDetail(
                    nilaiDetailList[i].id_nilai,
                    nilaiDetailList[i].nisn,
                    nilaiDetailList[i].nama_siswa,
                    nilaiDetailList[i].status_nilai
                )

                val resultUpdate = databaseHelper.updateTxNilaiDetail(nilaiDetail)
                if (i + 1 == nilaiDetailList.size) {
                    if (resultUpdate != -1) {
                        onUpdateSuccess()
                    } else {
                        onUpdateFailure()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Judul Nilai wajib diisi.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onUpdateSuccess() {
        Toast.makeText(
            this,
            "Berhasil memperbarui data.",
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }

    private fun onUpdateFailure() {
        Toast.makeText(
            this,
            "Gagal memperbarui data.",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun emptyLayoutMTD(msg: String) {
        binding.rvDetailNilai.gone()
        binding.emptyLayout.clEmpty.visible()
        binding.emptyLayout.tvEmptyDescription.text = msg
    }

    override fun onUploadNilaiUpdateEditText() {
        for (i in kelasMemberList.indices) {
            Log.d("Testing$i", kelasMemberList[i].nilai_temp.toString())
        }
    }

    override fun onUpdateNilaiUpdateEditText() {
        for (i in nilaiDetailList.indices) {
            Log.d("Testing$i", nilaiDetailList[i].status_nilai.toString())
        }
    }
}