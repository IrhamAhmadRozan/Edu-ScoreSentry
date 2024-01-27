package com.developer.rozan.eduscoresentry.view.list_kelas

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.rozan.eduscoresentry.data.entity.Kelas
import com.developer.rozan.eduscoresentry.data.sharedpref.UserPref
import com.developer.rozan.eduscoresentry.databinding.ActivityListKelasBinding
import com.developer.rozan.eduscoresentry.listener.OnKelasClickListener
import com.developer.rozan.eduscoresentry.util.constant.Constant.DELAY_INTENT
import com.developer.rozan.eduscoresentry.util.extension.gone
import com.developer.rozan.eduscoresentry.util.extension.visible
import com.developer.rozan.eduscoresentry.util.helper.DatabaseHelper
import com.developer.rozan.eduscoresentry.view.list_menu.ListMenuActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ListKelasActivity : AppCompatActivity(), OnKelasClickListener {

    private lateinit var binding: ActivityListKelasBinding
    private lateinit var databaseHelper: DatabaseHelper

    private var kelasList: List<Kelas> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListKelasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        UserPref.init = UserPref(this)

        databaseHelper = DatabaseHelper(applicationContext)

        binding.toolbarBack.layoutBack.setOnClickListener {
            finish()
        }

        binding.emptyLayout.btnTryAgain.setOnClickListener {
            initFirst()
        }

        initFirst()
    }

    private fun initFirst() {
        val token = UserPref.init.getToken()
        val user = UserPref.init.getUser()

        kelasList = if (user == "GURU") {
            databaseHelper.getListKelasByNIP(token)
        } else {
            databaseHelper.getListKelasAndKelasMemberByNISN(token)
        }

        if (kelasList.isNotEmpty()) {
            lifecycleScope.launch {
                delay(DELAY_INTENT)
                binding.rvKelas.visible()
                binding.emptyLayout.clEmpty.gone()
                setUpKelas()
            }
        } else {
            emptyLayoutMTD("Tidak ada kelas.")
        }
    }

    override fun onItemClicked(view: View, kelas: Kelas) {
        startActivity(Intent(this, ListMenuActivity::class.java).apply {
            putExtra(Kelas.KELAS, kelas)
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpKelas() {
        binding.rvKelas.layoutManager = LinearLayoutManager(this)
        val adapter = ListKelasGuruAdapter(kelasList)
        adapter.listener = this
        binding.rvKelas.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun emptyLayoutMTD(msg: String) {
        binding.rvKelas.gone()
        binding.emptyLayout.clEmpty.visible()
        binding.emptyLayout.tvEmptyDescription.text = msg
    }
}