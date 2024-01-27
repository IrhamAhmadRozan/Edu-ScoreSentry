package com.developer.rozan.eduscoresentry.view.list_menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import com.developer.rozan.eduscoresentry.R
import com.developer.rozan.eduscoresentry.data.entity.Kelas
import com.developer.rozan.eduscoresentry.data.sharedpref.UserPref
import com.developer.rozan.eduscoresentry.databinding.ActivityListMenuBinding
import com.developer.rozan.eduscoresentry.view.lihat_kehadiran.LihatKehadiranActivity
import com.developer.rozan.eduscoresentry.view.lihat_nilai.LihatNilaiActivity
import com.developer.rozan.eduscoresentry.view.upload_kehadiran.UploadKehadiranActivity
import com.developer.rozan.eduscoresentry.view.upload_nilai.UploadNilaiActivity

class ListMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListMenuBinding

    private var kelas: Kelas? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        UserPref.init = UserPref(this)

        val user = UserPref.init.getUser()

        if (user == "SISWA") {
            binding.ivMenu1.setImageDrawable(resources.getDrawable(R.drawable.ic_check_circle));
            binding.tvMenu1.text = "Nilai"

            binding.ivMenu2.setImageDrawable(resources.getDrawable(R.drawable.ic_assignment));
            binding.tvMenu2.text = "Kehadiran"
        }

        if (intent.getParcelableExtra<Parcelable>(Kelas.KELAS) != null)
            kelas = intent.getParcelableExtra(Kelas.KELAS)

        binding.toolbarBack.layoutBack.setOnClickListener {
            finish()
        }

        binding.cvUploadNilai.setOnClickListener {
            if (user == "GURU") {
                startActivity(Intent(this, UploadNilaiActivity::class.java).apply {
                    putExtra(Kelas.KELAS, kelas)
                })
            } else {
                startActivity(Intent(this, LihatNilaiActivity::class.java).apply {
                    putExtra(Kelas.KELAS, kelas)
                })
            }
        }

        binding.cvUploadKehadiran.setOnClickListener {
            if (user == "GURU") {
                startActivity(Intent(this, UploadKehadiranActivity::class.java).apply {
                    putExtra(Kelas.KELAS, kelas)
                })
            } else {
                startActivity(Intent(this, LihatKehadiranActivity::class.java).apply {
                    putExtra(Kelas.KELAS, kelas)
                })
            }
        }
    }
}