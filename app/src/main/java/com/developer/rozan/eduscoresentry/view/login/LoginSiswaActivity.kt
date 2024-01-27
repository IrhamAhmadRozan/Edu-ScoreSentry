package com.developer.rozan.eduscoresentry.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.Toast
import com.developer.rozan.eduscoresentry.R
import com.developer.rozan.eduscoresentry.data.sharedpref.UserPref
import com.developer.rozan.eduscoresentry.databinding.ActivityLoginSiswaBinding
import com.developer.rozan.eduscoresentry.util.helper.DatabaseHelper
import com.developer.rozan.eduscoresentry.view.dashboard.DashboardActivity
import com.google.android.material.textfield.TextInputLayout

class LoginSiswaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginSiswaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginSiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        UserPref.init = UserPref(this)

        val databaseHelper = DatabaseHelper(applicationContext)

        binding.rlLoginButtonSiswa.setOnClickListener {
            val nisn = binding.tfNisnSiswa.editText?.text.toString()
            val password = binding.tfPasswordSiswa.editText?.text.toString()

            if ((nisn.isNotEmpty()) and (password.isNotEmpty())) {
                val token = databaseHelper.siswaLogin(nisn, password)
                if (token != "") {
                    UserPref.init.loggedIn()
                    UserPref.init.saveToken(token)
                    UserPref.init.saveUser("SISWA")
                    startActivity(Intent(this, DashboardActivity::class.java))
                } else {
                    Toast.makeText(this, "Login Gagal! NISN belum terdaftar.", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(this, "NISN and Password must be field!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}