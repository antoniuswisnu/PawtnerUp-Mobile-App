package com.example.pawtnerup.di

import android.content.Context
import com.example.pawtnerup.data.model.TokenPreferences
import com.example.pawtnerup.data.model.dataStore
import com.example.pawtnerup.data.repository.TokenRepository

object Injection {
    fun provideRepository(context: Context): TokenRepository {
        val pref = TokenPreferences.getInstance(context.dataStore)
        return TokenRepository.getInstance(pref)
    }
}