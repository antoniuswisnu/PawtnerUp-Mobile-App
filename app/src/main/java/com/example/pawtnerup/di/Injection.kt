package com.example.pawtnerup.di

import android.content.Context
import com.example.pawtnerup.api.retrofit.ApiConfig
//import com.example.pawtnerup.data.pref.UserPreference
//import com.example.pawtnerup.data.pref.dataStore
import com.example.pawtnerup.data.repository.StoryRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {

//    fun provideRepository(context: Context): UserRepository {
//        val pref = UserPreference.getInstance(context.dataStore)
//        return UserRepository.getInstance(pref)
//    }

//    fun provideStoryRepository(context: Context): StoryRepository {
//        val userPreference = UserPreference.getInstance(context.dataStore)
//        val user = runBlocking { userPreference.getUser().first() }
//        val apiService = ApiConfig.getApiService(user.token)
//        return StoryRepository.getInstance(apiService)
//    }
}