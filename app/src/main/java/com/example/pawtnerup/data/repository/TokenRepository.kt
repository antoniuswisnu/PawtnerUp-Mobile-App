package com.example.pawtnerup.data.repository

import com.example.pawtnerup.data.model.TokenModel
import com.example.pawtnerup.data.model.TokenPreferences

class TokenRepository private constructor(private val tokenPreferences: TokenPreferences) {

    suspend fun saveSession(token: TokenModel) {
        tokenPreferences.saveToken(token)
    }

    fun getToken(): String {
        return tokenPreferences.getToken()
    }

    companion object{
        @Volatile
        private var instance: TokenRepository? = null

        fun getInstance(
            tokenPreferences: TokenPreferences
        ): TokenRepository =
            instance ?: synchronized(this) {
                instance ?: TokenRepository(tokenPreferences)
            }.also { instance = it}
    }
}