package com.developer.rozan.eduscoresentry.view.upload_kehadiran

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.rozan.eduscoresentry.data.entity.Kehadiran
import com.developer.rozan.eduscoresentry.data.entity.Kelas
import com.developer.rozan.eduscoresentry.databinding.ActivityUploadKehadiranBinding
import com.developer.rozan.eduscoresentry.listener.OnKehadiranClickListener
import com.developer.rozan.eduscoresentry.util.constant.Constant
import com.developer.rozan.eduscoresentry.util.extension.gone
import com.developer.rozan.eduscoresentry.util.extension.visible
import com.developer.rozan.eduscoresentry.util.helper.DatabaseHelper
import com.developer.rozan.eduscoresentry.view.upload_kehadiran.detail_upload_kehadiran.DetailUploadKehadiranActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UploadKehadiranActivity : AppCompatActivity(), OnKehadiranClickListener {

    private lateinit var binding: ActivityUploadKehadiranBinding
    private lateinit var databaseHelper: DatabaseHelper

    private var kelas: Kelas? = null

    private var kehadiranList: List<Kehadiran> = mutableListOf()

    private val ADD_KEHADIRAN = 201
    private val UPDATE_KEHADIRAN = 202

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadKehadiranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(applicationContext)

        if (intent.getParcelableExtra<Parcelable>(Kelas.KELAS) != null)
            kelas = intent.getParcelableExtra(Kelas.KELAS)

        binding.toolbarBack.titleToolbarBack.text = "Kehadiran"
        binding.toolbarBack.layoutAction1.visible()

        binding.toolbarBack.layoutBack.setOnClickListener {
            finish()
        }

        binding.toolbarBack.layoutAction1.setOnClickListener {
            val intent = Intent(this, DetailUploadKehadiranActivity::class.java)
            intent.putExtra(Kelas.KELAS, kelas)
            intent.putExtra("action", "add")
            startActivityForResult(intent, ADD_KEHADIRAN)
        }

        binding.emptyLayout.btnTryAgain.setOnClickListener {
            initFirst()
        }

        initFirst()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ADD_KEHADIRAN -> {
                initFirst()
            }

            UPDATE_KEHADIRAN -> {
                initFirst()
            }

            else -> {
                Toast.makeText(this, "Ups, terjadi kesalahan!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initFirst() {
        kehadiranList = databaseHelper.getListKehadiranByIdKelas(kelas!!.id_kelas)
        if (kehadiranList.isNotEmpty()) {
            lifecycleScope.launch {
                delay(Constant.DELAY_INTENT)
                binding.rvKehadiran.visible()
                binding.emptyLayout.clEmpty.gone()
                setUpKehadiran()
            }
        } else {
            emptyLayoutMTD("Belum ada kehadiran yang ditambahkan.")
        }
    }

    override fun onItemClicked(view: View, kehadiran: Kehadiran) {
        val intent = Intent(this, DetailUploadKehadiranActivity::class.java)
        intent.putExtra(Kehadiran.KEHADIRAN, kehadiran)
        intent.putExtra("action", "update")
        startActivityForResult(intent, UPDATE_KEHADIRAN)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpKehadiran() {
        binding.rvKehadiran.layoutManager = LinearLayoutManager(this)
        val adapter = UploadKehadiranAdapter(kehadiranList)
        adapter.listener = this
        binding.rvKehadiran.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun emptyLayoutMTD(msg: String) {
        binding.rvKehadiran.gone()
        binding.emptyLayout.clEmpty.visible()
        binding.emptyLayout.tvEmptyDescription.text = msg
    }
}