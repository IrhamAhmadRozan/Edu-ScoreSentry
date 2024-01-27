package com.developer.rozan.eduscoresentry.view.upload_nilai

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.rozan.eduscoresentry.data.entity.Kelas
import com.developer.rozan.eduscoresentry.data.entity.Nilai
import com.developer.rozan.eduscoresentry.databinding.ActivityUploadNilaiBinding
import com.developer.rozan.eduscoresentry.listener.OnNilaiClickListener
import com.developer.rozan.eduscoresentry.util.constant.Constant
import com.developer.rozan.eduscoresentry.util.extension.gone
import com.developer.rozan.eduscoresentry.util.extension.visible
import com.developer.rozan.eduscoresentry.util.helper.DatabaseHelper
import com.developer.rozan.eduscoresentry.view.upload_nilai.detail_upload_nilai.DetailUploadNilaiActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UploadNilaiActivity : AppCompatActivity(), OnNilaiClickListener {

    private lateinit var binding: ActivityUploadNilaiBinding
    private lateinit var databaseHelper: DatabaseHelper

    private var kelas: Kelas? = null

    private var nilaiList: List<Nilai> = mutableListOf()

    private val ADD_NILAI = 101
    private val UPDATE_NILAI = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadNilaiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(applicationContext)

        if (intent.getParcelableExtra<Parcelable>(Kelas.KELAS) != null)
            kelas = intent.getParcelableExtra(Kelas.KELAS)

        binding.toolbarBack.titleToolbarBack.text = "Nilai"
        binding.toolbarBack.layoutAction1.visible()

        binding.toolbarBack.layoutBack.setOnClickListener {
            finish()
        }

        binding.toolbarBack.layoutAction1.setOnClickListener {
            val intent = Intent(this, DetailUploadNilaiActivity::class.java)
            intent.putExtra(Kelas.KELAS, kelas)
            intent.putExtra("action", "add")
            startActivityForResult(intent, ADD_NILAI)
        }

        binding.emptyLayout.btnTryAgain.setOnClickListener {
            initFirst()
        }

        initFirst()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ADD_NILAI -> {
                initFirst()
            }

            UPDATE_NILAI -> {
                initFirst()
            }

            else -> {
                Toast.makeText(this, "Ups, terjadi kesalahan!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initFirst() {
        nilaiList = databaseHelper.getListNilaiByIdKelas(kelas!!.id_kelas)
        if (nilaiList.isNotEmpty()) {
            lifecycleScope.launch {
                delay(Constant.DELAY_INTENT)
                binding.rvUploadNilai.visible()
                binding.emptyLayout.clEmpty.gone()
                setUpNilai()
            }
        } else {
            emptyLayoutMTD("Belum ada nilai yang ditambahkan.")
        }
    }

    override fun onItemClicked(view: View, nilai: Nilai) {
        val intent = Intent(this, DetailUploadNilaiActivity::class.java)
        intent.putExtra(Nilai.NILAI, nilai)
        intent.putExtra("action", "update")
        startActivityForResult(intent, UPDATE_NILAI)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpNilai() {
        binding.rvUploadNilai.layoutManager = LinearLayoutManager(this)
        val adapter = UploadNilaiAdapter(nilaiList)
        adapter.listener = this
        binding.rvUploadNilai.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun emptyLayoutMTD(msg: String) {
        binding.rvUploadNilai.gone()
        binding.emptyLayout.clEmpty.visible()
        binding.emptyLayout.tvEmptyDescription.text = msg
    }
}