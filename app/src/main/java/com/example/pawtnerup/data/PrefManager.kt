package com.example.pawtnerup.data

import android.content.Context
import android.content.SharedPreferences

class PrefManager private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences
    companion object{

        private const val PREFS_FILENAME = "TokenPrefs"
        private const val KEY_TOKEN = "token"
        private const val KEY_TOKEN2 = "token2"

        @Volatile
        private var instance: PrefManager? = null

        fun getInstance(context: Context): PrefManager {
            return instance ?: synchronized(this) {
                instance ?: PrefManager(context.applicationContext).also {
                    instance = it
                }
            }
        }


    }

    init {
        sharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    fun clear() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun saveToken2(token: String) {
        sharedPreferences.edit().putString(KEY_TOKEN2, token).apply()
    }

    fun getToken2(): String? {
        return sharedPreferences.getString(KEY_TOKEN2, null)
    }



}