package com.developer.rozan.eduscoresentry.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.Toast
import com.developer.rozan.eduscoresentry.R
import com.developer.rozan.eduscoresentry.view.dashboard.DashboardActivity
import com.google.android.material.textfield.TextInputLayout

class LoginWaliMuridActivity : AppCompatActivity() {

    private lateinit var tfNisnWaliMurid : TextInputLayout
    private lateinit var tfEmailWaliMurid : TextInputLayout
    private lateinit var tfPasswordWaliMurid : TextInputLayout
    private lateinit var rlLoginButtonWaliMurid : RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_wali_murid)

        tfNisnWaliMurid = findViewById(R.id.tf_nisn_wali_murid)
        tfEmailWaliMurid = findViewById(R.id.tf_email_wali_murid)
        tfPasswordWaliMurid = findViewById(R.id.tf_password_wali_murid)
        rlLoginButtonWaliMurid = findViewById(R.id.rl_login_button_wali_murid)

        rlLoginButtonWaliMurid.setOnClickListener {
            val nisn = tfNisnWaliMurid.editText?.text.toString()
            val email = tfEmailWaliMurid.editText?.text.toString()
            val password = tfPasswordWaliMurid.editText?.text.toString()

            if ((nisn.isNotEmpty()) and (email.isNotEmpty()) and (password.isNotEmpty())) {
                startActivity(Intent(this, DashboardActivity::class.java))
            } else {
                Toast.makeText(this, "NISN, Email, and Password must be field!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}