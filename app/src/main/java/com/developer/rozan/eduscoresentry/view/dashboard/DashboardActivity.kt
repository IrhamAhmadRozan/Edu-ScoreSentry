package com.developer.rozan.eduscoresentry.view.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.developer.rozan.eduscoresentry.R
import com.developer.rozan.eduscoresentry.data.sharedpref.UserPref
import com.developer.rozan.eduscoresentry.databinding.ActivityDashboardBinding
import com.developer.rozan.eduscoresentry.view.dialog.DialogKonfirmasi
import com.developer.rozan.eduscoresentry.view.home.HomeFragment
import com.developer.rozan.eduscoresentry.view.profile.ProfileFragment
import com.developer.rozan.eduscoresentry.view.splashscreen.SplashScreenActivity
import com.google.android.material.snackbar.Snackbar

class DashboardActivity : AppCompatActivity(), DialogKonfirmasi.DialogConfirmationListener {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        UserPref.init = UserPref(this)

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_beranda -> {
                    replaceFragment(HomeFragment())
                    true
                }
//                R.id.navigation_inbox -> {
//                    replaceFragment(InboxFragment())
//                    true
//                }
                R.id.navigation_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }

                else -> {
                    Toast.makeText(this, "Error Pokoknya", Toast.LENGTH_SHORT).show()
                    false
                }
            }
        }
        replaceFragment(HomeFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit()
    }

    override fun onDialogConfirmationListener(param: String?) {
        if (param == "LOGOUT") {
            UserPref.init.logout()
            startActivity(Intent(this, SplashScreenActivity::class.java))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        startActivity(intent)
    }
}