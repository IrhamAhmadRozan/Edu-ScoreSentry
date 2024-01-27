package com.developer.rozan.eduscoresentry.data.sharedpref

import android.content.Context
import android.content.SharedPreferences

class UserPref(context: Context) {

    companion object {

        private const val SHARED_PREFERENCES_NAME = "USER_PREFERENCES"

        private const val IS_LOGIN = "IS_LOGIN"

        private const val PREF_TOKEN = "PREF_TOKEN"

        private const val PREF_USER = "PREF_USER"

        lateinit var init: UserPref
    }

    private val sharedPreferences: SharedPreferences

    init {
        this.sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun loggedIn() {
        this.sharedPreferences.edit()
            .putBoolean(IS_LOGIN, true)
            .apply()
    }

    fun isLogin(): Boolean {
        return this.sharedPreferences.getBoolean(IS_LOGIN, false)
    }

    fun logout() {
        this.sharedPreferences.edit()
            .clear()
            .apply()
    }

    fun saveToken(token: String) {
        this.sharedPreferences.edit()
            .putString(PREF_TOKEN, token)
            .apply()
    }

    fun getToken(): String {
        return sharedPreferences.getString(PREF_TOKEN, null) ?: return ""
    }

    fun saveUser(token: String) {
        this.sharedPreferences.edit()
            .putString(PREF_USER, token)
            .apply()
    }

    fun getUser(): String {
        return sharedPreferences.getString(PREF_USER, null) ?: return ""
    }
}