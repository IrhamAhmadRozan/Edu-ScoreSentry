package com.developer.rozan.eduscoresentry.view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.developer.rozan.eduscoresentry.R
import com.developer.rozan.eduscoresentry.data.entity.Guru
import com.developer.rozan.eduscoresentry.data.entity.Siswa
import com.developer.rozan.eduscoresentry.data.sharedpref.UserPref
import com.developer.rozan.eduscoresentry.databinding.FragmentProfileBinding
import com.developer.rozan.eduscoresentry.util.extension.gone
import com.developer.rozan.eduscoresentry.util.helper.DatabaseHelper
import com.developer.rozan.eduscoresentry.view.dialog.DialogKonfirmasi

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var databaseHelper: DatabaseHelper

    private var guru: Guru? = null
    private var siswa: Siswa? = null
    private val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DatabaseHelper(requireActivity())

        val user = UserPref.init.getUser()

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

        binding.toolbarMain.titleToolbar.text = "Profile"

        binding.clLogout.setOnClickListener {
            binding.clProfile.gone()
            customDialog("Apakah Anda yakin untuk keluar?")
        }
    }

    private fun customDialog(message: String?) {
        val fragment = DialogKonfirmasi()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_dialog, fragment)
        bundle.putString("DescMsg", message)
        fragment.arguments = bundle
        transaction.commit()
    }
}