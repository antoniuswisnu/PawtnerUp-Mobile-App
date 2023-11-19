package com.example.pawtnerup.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pawtnerup.data.pref.LoginPreferences
import com.example.pawtnerup.ui.favorite.StoriesViewModel

class ViewModelFactory constructor(private val application: Application, private val userPreference: LoginPreferences)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(StoriesViewModel::class.java) -> {
                StoriesViewModel(application, userPreference) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(
            application: Application,
            userPreference: LoginPreferences
        ): ViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(application, userPreference)
            }
    }
}