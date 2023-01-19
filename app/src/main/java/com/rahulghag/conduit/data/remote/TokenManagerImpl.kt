package com.rahulghag.conduit.data.remote

import android.content.Context
import android.content.SharedPreferences

class TokenManagerImpl(context: Context) : TokenManager {
    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREF_TOKEN, Context.MODE_PRIVATE)

    override fun saveToken(token: String) {
        val editor = preferences.edit()
        editor.putString(PREF_TOKEN, token)
        editor.apply()
    }

    override fun getToken(): String? {
        return preferences.getString(PREF_TOKEN, null)
    }

    override fun deleteToken() {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        const val PREF_TOKEN = "pref_token"
    }
}