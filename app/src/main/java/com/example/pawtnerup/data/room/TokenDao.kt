package com.example.pawtnerup.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TokenDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertToken(tokenEntity: TokenEntity)

    @Query("SELECT * FROM token_table")
    fun getAllTokens(): LiveData<List<TokenEntity>>

    @Query("SELECT * FROM token_table WHERE id = :id")
    fun getToken(id: Int): TokenEntity
}