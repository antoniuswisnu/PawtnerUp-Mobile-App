package com.example.pawtnerup.ui.login

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.pawtnerup.data.room.TokenEntity
import com.example.pawtnerup.data.room.TokenRoomRepository

class LoginTokenViewModel(application: Application) : ViewModel() {

    private val mTokenRepository: TokenRoomRepository = TokenRoomRepository(application)

    fun insert(token: TokenEntity) {
        mTokenRepository.insertToken(token)
    }

}