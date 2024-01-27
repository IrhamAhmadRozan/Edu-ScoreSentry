package com.developer.rozan.eduscoresentry.view.lihat_nilai

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.rozan.eduscoresentry.data.entity.Kelas
import com.developer.rozan.eduscoresentry.data.entity.Nilai
import com.developer.rozan.eduscoresentry.databinding.ActivityLihatNilaiBinding
import com.developer.rozan.eduscoresentry.util.constant.Constant
import com.developer.rozan.eduscoresentry.util.extension.gone
import com.developer.rozan.eduscoresentry.util.extension.visible
import com.developer.rozan.eduscoresentry.util.helper.DatabaseHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LihatNilaiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLihatNilaiBinding
    private lateinit var databaseHelper: DatabaseHelper

    private var kelas: Kelas? = null

    private var nilaiList: List<Nilai> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLihatNilaiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(applicationContext)

        if (intent.getParcelableExtra<Parcelable>(Kelas.KELAS) != null)
            kelas = intent.getParcelableExtra(Kelas.KELAS)

        binding.toolbarBack.titleToolbarBack.text = "Nilai"

        binding.toolbarBack.layoutBack.setOnClickListener {
            finish()
        }

        binding.emptyLayout.btnTryAgain.setOnClickListener {
            initFirst()
        }

        initFirst()
    }

    private fun initFirst() {
        nilaiList = databaseHelper.getListNilaiAndNilaiDetailByIdKelasAndNISN(
            kelas!!.id_kelas,
            kelas!!.kelas_member!!.nisn
        )
        if (nilaiList.isNotEmpty()) {
            lifecycleScope.launch {
                delay(Constant.DELAY_INTENT)
                binding.rvLihatNilai.visible()
                binding.emptyLayout.clEmpty.gone()
                setUpNilai()
            }
        } else {
            emptyLayoutMTD("Belum ada nilai yang ditambahkan.")
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpNilai() {
        binding.rvLihatNilai.layoutManager = LinearLayoutManager(this)
        val adapter = LihatNilaiAdapter(nilaiList)
        binding.rvLihatNilai.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun emptyLayoutMTD(msg: String) {
        binding.rvLihatNilai.gone()
        binding.emptyLayout.clEmpty.visible()
        binding.emptyLayout.tvEmptyDescription.text = msg
    }
}