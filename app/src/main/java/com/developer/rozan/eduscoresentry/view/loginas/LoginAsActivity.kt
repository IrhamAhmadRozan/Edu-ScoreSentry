package com.developer.rozan.eduscoresentry.view.loginas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import com.developer.rozan.eduscoresentry.R
import com.developer.rozan.eduscoresentry.databinding.ActivityLoginAsBinding
import com.developer.rozan.eduscoresentry.view.login.LoginGuruActivity
import com.developer.rozan.eduscoresentry.view.login.LoginSiswaActivity
import com.developer.rozan.eduscoresentry.view.login.LoginWaliMuridActivity

class LoginAsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginAsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rlGuruButton.setOnClickListener {
            startActivity(Intent(this, LoginGuruActivity::class.java))
        }

        binding.rlSiswaButton.setOnClickListener {
            startActivity(Intent(this, LoginSiswaActivity::class.java))
        }

//        binding.rlWaliSiswaButton.setOnClickListener {
//            startActivity(Intent(this, LoginWaliMuridActivity::class.java))
//        }
    }
}