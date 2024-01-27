package com.developer.rozan.eduscoresentry.util

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}