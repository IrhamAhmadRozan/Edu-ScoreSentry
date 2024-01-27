package com.developer.rozan.eduscoresentry.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.Toast
import com.developer.rozan.eduscoresentry.R
import com.developer.rozan.eduscoresentry.data.sharedpref.UserPref
import com.developer.rozan.eduscoresentry.databinding.ActivityLoginGuruBinding
import com.developer.rozan.eduscoresentry.util.helper.DatabaseHelper
import com.developer.rozan.eduscoresentry.view.dashboard.DashboardActivity
import com.google.android.material.textfield.TextInputLayout

class LoginGuruActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginGuruBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginGuruBinding.inflate(layoutInflater)
        setContentView(binding.root)

        UserPref.init = UserPref(this)

        val databaseHelper = DatabaseHelper(applicationContext)

        binding.rlLoginButtonGuru.setOnClickListener {
            val nip = binding.tfNipGuru.editText?.text.toString()
            val password = binding.tfPasswordGuru.editText?.text.toString()

            if ((nip.isNotEmpty()) and (password.isNotEmpty())) {
                val token = databaseHelper.guruLogin(nip, password)
                if (token != "") {
                    UserPref.init.loggedIn()
                    UserPref.init.saveToken(token)
                    UserPref.init.saveUser("GURU")
                    startActivity(Intent(this, DashboardActivity::class.java))
                } else {
                    Toast.makeText(this, "Login Gagal! NIP belum terdaftar.", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(this, "NIP and Password must be field!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
}