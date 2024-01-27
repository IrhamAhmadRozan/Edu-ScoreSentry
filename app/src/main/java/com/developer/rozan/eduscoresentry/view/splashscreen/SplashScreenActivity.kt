package com.developer.rozan.eduscoresentry.view.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import com.developer.rozan.eduscoresentry.R
import com.developer.rozan.eduscoresentry.data.sharedpref.UserPref
import com.developer.rozan.eduscoresentry.databinding.ActivitySplashScreenBinding
import com.developer.rozan.eduscoresentry.view.dashboard.DashboardActivity
import com.developer.rozan.eduscoresentry.view.loginas.LoginAsActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        UserPref.init = UserPref(this)

        binding.image.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
        lifecycleScope.launch {
            delay(1500L)
            toNextActivity()
            finish()
        }
    }

    private fun toNextActivity() {
        if (UserPref.init.isLogin()) {
            startActivity(Intent(this, DashboardActivity::class.java))
        } else {
            startActivity(Intent(this, LoginAsActivity::class.java))
        }
    }
}