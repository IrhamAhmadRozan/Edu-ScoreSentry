package com.developer.rozan.eduscoresentry.view.lihat_kehadiran

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.rozan.eduscoresentry.data.entity.Kehadiran
import com.developer.rozan.eduscoresentry.data.entity.Kelas
import com.developer.rozan.eduscoresentry.databinding.ActivityLihatKehadiranBinding
import com.developer.rozan.eduscoresentry.util.constant.Constant
import com.developer.rozan.eduscoresentry.util.extension.gone
import com.developer.rozan.eduscoresentry.util.extension.visible
import com.developer.rozan.eduscoresentry.util.helper.DatabaseHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LihatKehadiranActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLihatKehadiranBinding
    private lateinit var databaseHelper: DatabaseHelper

    private var kelas: Kelas? = null

    private var kehadiranList: List<Kehadiran> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLihatKehadiranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(applicationContext)

        if (intent.getParcelableExtra<Parcelable>(Kelas.KELAS) != null)
            kelas = intent.getParcelableExtra(Kelas.KELAS)

        binding.toolbarBack.titleToolbarBack.text = "Kehadiran"

        binding.toolbarBack.layoutBack.setOnClickListener {
            finish()
        }

        binding.emptyLayout.btnTryAgain.setOnClickListener {
            initFirst()
        }

        initFirst()
    }

    private fun initFirst() {
        kehadiranList = databaseHelper.getListKehadiranAndKehadiranDetailByIdKelasAndNISN(
            kelas!!.id_kelas,
            kelas!!.kelas_member!!.nisn
        )
        if (kehadiranList.isNotEmpty()) {
            lifecycleScope.launch {
                delay(Constant.DELAY_INTENT)
                binding.rvLihatKehadiran.visible()
                binding.emptyLayout.clEmpty.gone()
                setUpNilai()
            }
        } else {
            emptyLayoutMTD("Belum ada kehadiran yang ditambahkan.")
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpNilai() {
        binding.rvLihatKehadiran.layoutManager = LinearLayoutManager(this)
        val adapter = LihatKehadiranAdapter(kehadiranList)
        binding.rvLihatKehadiran.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun emptyLayoutMTD(msg: String) {
        binding.rvLihatKehadiran.gone()
        binding.emptyLayout.clEmpty.visible()
        binding.emptyLayout.tvEmptyDescription.text = msg
    }
}