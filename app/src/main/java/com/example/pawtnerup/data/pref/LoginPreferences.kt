package com.example.pawtnerup.data.pref

import android.content.Context
import com.example.pawtnerup.api.response.LoginResult

class LoginPreferences(context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setLogin(value: LoginResult) {
        val editor = preferences.edit()
        editor.putString(NAME, value.name)
        editor.putString(USER_ID, value.userId)
        editor.putString(TOKEN, value.token)
        editor.apply()
    }

    fun getString(key: String): String? {
        return preferences.getString(key, null)
    }

    fun getUser(): LoginResult {
//        val name = preferences.getString(NAME, null)
//        val userId = preferences.getString(USER_ID, null)
//        val token = preferences.getString(TOKEN, null)
        val name = "wisnu"
        val userId = "user-9vFfqNiAnbY6SFna"
        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTl2RmZxTmlBbmJZNlNGbmEiLCJpYXQiOjE2OTk5ODk0MTl9.DxF40mLNRl4cacVn9A0B_CYCyvUe9OqbXBE1EOypsFc"
        return LoginResult(userId, name, token)
    }

    fun removeUser() {
        val editor = preferences.edit().clear()
        editor.apply()
    }

    companion object {
        const val PREFS_NAME = "login_pref"
        const val NAME = "name"
        const val USER_ID = "userId"
        const val TOKEN = "token"
    }
}