package com.developer.rozan.eduscoresentry.view.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.developer.rozan.eduscoresentry.data.entity.Guru
import com.developer.rozan.eduscoresentry.data.entity.Siswa
import com.developer.rozan.eduscoresentry.data.sharedpref.UserPref
import com.developer.rozan.eduscoresentry.databinding.FragmentHomeBinding
import com.developer.rozan.eduscoresentry.util.helper.DatabaseHelper
import com.developer.rozan.eduscoresentry.view.list_kelas.ListKelasActivity

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var databaseHelper: DatabaseHelper

    private var guru: Guru? = null
    private var siswa: Siswa? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DatabaseHelper(requireActivity())

        val user = UserPref.init.getUser()

        binding.toolbarMain.titleToolbar.text = "Edu ScoreSentry"

        if (user == "GURU") {
            guru = databaseHelper.getGuruByNIP(UserPref.init.getToken())
            binding.tvProfileName.text = guru!!.nama_guru
            binding.tvProfileNipornisn.text = guru!!.nip
            binding.tvNotelp.text = guru!!.no_telp
        } else {
            siswa = databaseHelper.getSiswaByNISN(UserPref.init.getToken())
            binding.tvProfileName.text = siswa!!.nama_siswa
            binding.tvProfileNipornisn.text = siswa!!.nisn
            binding.tvNotelp.text = siswa!!.no_telp
        }

        binding.cvDaftarKelas.setOnClickListener {
            startActivity(Intent(requireActivity(), ListKelasActivity::class.java))
        }

        binding.cvHubungiAdmin.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/6282293958011"))
            startActivity(intent)
        }

        binding.cvTerkaitSekolah.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://itpln.ac.id"))
            startActivity(intent)
        }
    }
}