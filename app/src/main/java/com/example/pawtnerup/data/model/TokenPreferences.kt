package com.example.pawtnerup.data.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class TokenPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveToken(token: TokenModel) {
        dataStore.edit { preferences ->
            preferences[refreshToken] = token.refreshToken.toString()
        }
    }

//    fun getToken(): Flow<TokenModel> {
//      return dataStore.data.map {preferences ->
//          TokenModel(
//              preferences[refreshToken] ?: ""
//          )
//      }
//    }

    fun getToken(): String {
        // return token
        return dataStore.data.map {preferences ->
            preferences[refreshToken] ?: ""
        }.toString()
    }

    companion object{

        @Volatile
        private var INSTANCE: TokenPreferences? = null

        private val refreshToken = stringPreferencesKey("refreshToken")

        fun getInstance(dataStore: DataStore<Preferences>): TokenPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = TokenPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}