package com.developer.rozan.eduscoresentry.view.upload_kehadiran.detail_upload_kehadiran

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.rozan.eduscoresentry.data.entity.Kehadiran
import com.developer.rozan.eduscoresentry.data.entity.KehadiranDetail
import com.developer.rozan.eduscoresentry.data.entity.Kelas
import com.developer.rozan.eduscoresentry.data.entity.KelasMember
import com.developer.rozan.eduscoresentry.databinding.ActivityDetailUploadKehadiranBinding
import com.developer.rozan.eduscoresentry.listener.OnStatusKehadiranInsertClickListener
import com.developer.rozan.eduscoresentry.listener.OnStatusKehadiranUpdateClickListener
import com.developer.rozan.eduscoresentry.util.constant.Constant
import com.developer.rozan.eduscoresentry.util.extension.gone
import com.developer.rozan.eduscoresentry.util.extension.visible
import com.developer.rozan.eduscoresentry.util.helper.DatabaseHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailUploadKehadiranActivity : AppCompatActivity(),
    OnStatusKehadiranInsertClickListener, OnStatusKehadiranUpdateClickListener {

    private lateinit var binding: ActivityDetailUploadKehadiranBinding
    private lateinit var databaseHelper: DatabaseHelper

    private var kelas: Kelas? = null
    private var kehadiran: Kehadiran? = null
    private var action: String = ""

    private var kelasMemberList: List<KelasMember> = mutableListOf()
    private var kehadiranDetailList: List<KehadiranDetail> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUploadKehadiranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(applicationContext)

        if (intent != null) {
            kelas = intent.getParcelableExtra(Kelas.KELAS)
            kehadiran = intent.getParcelableExtra(Kehadiran.KEHADIRAN)
            action = intent.getStringExtra("action").toString()
        }

        binding.toolbarBack.layoutAction2.visible()

        binding.toolbarBack.layoutBack.setOnClickListener {
            finish()
        }

        binding.toolbarBack.layoutAction2.setOnClickListener {
            if (action == "add") {
                setUpInsertKehadiran()
            } else {
                setUpUpdateKehadiran()
            }
        }

        binding.emptyLayout.btnTryAgain.setOnClickListener {
            initFirst()
        }

        initFirst()
    }

    private fun initFirst() {
        if (action == "add") {
            binding.toolbarBack.titleToolbarBack.text = "Tambah Kehadiran"
            kelasMemberList = databaseHelper.getListKelasMemberByIdKelas(kelas!!.id_kelas)
            if (kelasMemberList.isNotEmpty()) {
                lifecycleScope.launch {
                    delay(Constant.DELAY_INTENT)
                    binding.rvDetailKehadiran.visible()
                    binding.emptyLayout.clEmpty.gone()
                    setUpKehadiran(action)
                }
            } else {
                emptyLayoutMTD("Tidak ada siswa.")
            }
        } else {
            binding.toolbarBack.titleToolbarBack.text = "Ubah Kehadiran"
            kehadiranDetailList =
                databaseHelper.getListKehadiranDetailByIdNilai(kehadiran!!.id_kehadiran)
            if (kehadiranDetailList.isNotEmpty()) {
                lifecycleScope.launch {
                    delay(Constant.DELAY_INTENT)
                    binding.rvDetailKehadiran.visible()
                    binding.emptyLayout.clEmpty.gone()
                    setUpKehadiran(action)
                }
            } else {
                emptyLayoutMTD("Tidak ada siswa.")
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpKehadiran(action: String) {
        binding.rvDetailKehadiran.layoutManager = LinearLayoutManager(this)
        if (action == "add") {
            var adapter = DetailUploadKehadiranAdapter(kelasMemberList)
            adapter.listener = this
            binding.rvDetailKehadiran.adapter = adapter
            adapter.notifyDataSetChanged()
        } else {
            binding.tfDetailJudulKehadiran.setText(kehadiran!!.judul_kehadiran)
            var adapter = DetailUpdateKehadiranAdapter(kehadiranDetailList)
            adapter.listener = this
            binding.rvDetailKehadiran.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("NewApi")
    private fun setUpInsertKehadiran() {
        val judulKehadiran = binding.tfDetailJudulKehadiran.text.toString()

        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        val current = LocalDateTime.now().format(formatter)

        if (judulKehadiran.isNotEmpty() && kelasMemberList[0].kehadiran_temp != "") {
            val idFormatter = "Kehadiran-" + kelas?.id_kelas + "-" + kelas?.nip + "-" + current
            val kehadiran = Kehadiran(idFormatter, kelas?.id_kelas!!, judulKehadiran)
            databaseHelper.insertTxKehadiran(kehadiran)

            for (i in kelasMemberList.indices) {
                val kehadiranDetail = KehadiranDetail(
                    kehadiran.id_kehadiran,
                    kelasMemberList[i].nisn,
                    "",
                    kelasMemberList[i].kehadiran_temp
                )

                val resultInsert = databaseHelper.insertTxKehadiranDetail(kehadiranDetail)
                if (i + 1 == kelasMemberList.size) {
                    if (resultInsert.toInt() != -1) {
                        onInsertSuccess(i + 1)
                    } else {
                        onInsertFailure()
                    }
                }
            }
        } else {
            Toast.makeText(
                this,
                "Judul Kehadiran dan Status Kehadiran wajib diisi.",
                Toast.LENGTH_SHORT
            ).show()
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

    private fun setUpUpdateKehadiran() {
        val judulKehadiran = binding.tfDetailJudulKehadiran.text.toString()
        val updateKehadiran =
            Kehadiran(kehadiran!!.id_kehadiran, kehadiran!!.id_kelas, judulKehadiran)
        databaseHelper.updateTxKehadiran(updateKehadiran)

        if (judulKehadiran.isNotEmpty()) {
            for (i in kehadiranDetailList.indices) {
                val kehadiranDetail = KehadiranDetail(
                    kehadiranDetailList[i].id_kehadiran,
                    kehadiranDetailList[i].nisn,
                    kehadiranDetailList[i].nama_siswa,
                    kehadiranDetailList[i].status_kehadiran
                )

                val resultUpdate = databaseHelper.updateTxKehadiranDetail(kehadiranDetail)
                if (i + 1 == kehadiranDetailList.size) {
                    if (resultUpdate != -1) {
                        onUpdateSuccess()
                    } else {
                        onUpdateFailure()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Judul Kehadiran wajib diisi.", Toast.LENGTH_SHORT).show()
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
        binding.rvDetailKehadiran.gone()
        binding.emptyLayout.clEmpty.visible()
        binding.emptyLayout.tvEmptyDescription.text = msg
    }

    override fun onItemClicked(view: View, kelasMember: KelasMember) {
        for (i in kelasMemberList.indices) {
            Log.d("Testing Kehadiran$i", kelasMemberList[i].kehadiran_temp)
        }
    }

    override fun onItemClicked(view: View, kehadiranDetail: KehadiranDetail) {
        for (i in kehadiranDetailList.indices) {
            Log.d("Testing Kehadiran$i", kehadiranDetailList[i].status_kehadiran)
        }
    }
}