package com.example.pawtnerup.data.room

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TokenRoomRepository(application: Application) {

    private val mTokenDao: TokenDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = TokenDatabase.getDatabase(application)
        mTokenDao = db.tokenDao()
    }

    fun insertToken(tokenEntity: TokenEntity) {
        executorService.execute { mTokenDao.insertToken(tokenEntity) }
    }

    fun getAllTokens(): LiveData<List<TokenEntity>> = mTokenDao.getAllTokens()

    fun getToken(id: Int) = mTokenDao.getToken(id)

}