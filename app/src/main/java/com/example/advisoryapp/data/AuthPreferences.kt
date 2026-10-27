package com.example.advisoryapp.data

import android.content.Context

object AuthPreferences {
    private const val PREF_NAME = "aqviora_auth_prefs"
    private const val KEY_REMEMBER_ME = "remember_me"
    private const val KEY_SAVED_EMAIL = "saved_email"
    private const val KEY_SAVED_PASS = "saved_pass"

    fun saveRememberMe(context: Context, remember: Boolean, email: String, pass: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        if (remember) {
            prefs.edit()
                .putBoolean(KEY_REMEMBER_ME, true)
                .putString(KEY_SAVED_EMAIL, email)
                .putString(KEY_SAVED_PASS, pass)
                .apply()
        } else {
            prefs.edit().clear().apply()
        }
    }

    fun getSavedAuth(context: Context): Triple<Boolean, String, String> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val remember = prefs.getBoolean(KEY_REMEMBER_ME, false)
        val email = if (remember) prefs.getString(KEY_SAVED_EMAIL, "") ?: "" else ""
        val pass = if (remember) prefs.getString(KEY_SAVED_PASS, "") ?: "" else ""
        return Triple(remember, email, pass)
    }
}
