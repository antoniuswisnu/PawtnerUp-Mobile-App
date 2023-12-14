package com.example.pawtnerup.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawtnerup.data.model.TokenModel
import com.example.pawtnerup.data.repository.TokenRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val tokenRepository: TokenRepository) : ViewModel() {

    fun saveSession(user: TokenModel) {
        viewModelScope.launch {
            tokenRepository.saveSession(user)
        }
    }
    fun getToken() = tokenRepository.getToken()
}