package com.example.pawtnerup.data.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "token_table")
@Parcelize
data class TokenEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val token: String
) : Parcelable