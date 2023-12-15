package com.example.pawtnerup.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.pawtnerup.data.room.TokenEntity
import com.example.pawtnerup.data.room.TokenRoomRepository

class MainViewModel(application: Application) : ViewModel(){
    private val mTokenRepository: TokenRoomRepository = TokenRoomRepository(application)

    fun getToken() : LiveData<List<TokenEntity>> = mTokenRepository.getAllTokens()

    fun getToken(id: Int) = mTokenRepository.getToken(id)


}