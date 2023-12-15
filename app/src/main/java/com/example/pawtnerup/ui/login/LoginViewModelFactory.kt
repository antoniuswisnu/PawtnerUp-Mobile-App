package com.example.pawtnerup.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pawtnerup.data.repository.TokenRepository
import com.example.pawtnerup.di.Injection

class LoginViewModelFactory(private val repository: TokenRepository) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

        companion object {
        @Volatile
        private var INSTANCE: LoginViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): LoginViewModelFactory {
            if (INSTANCE == null) {
                synchronized(LoginViewModelFactory::class.java) {
                    INSTANCE = LoginViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as LoginViewModelFactory
        }
    }
}

