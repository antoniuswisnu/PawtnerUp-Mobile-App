package com.example.pawtnerup.data.room

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TokenEntity::class], version = 1, exportSchema = false)
abstract class TokenDatabase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao

    companion object {
        @Volatile
        private var INSTANCE: TokenDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): TokenDatabase {
            if (INSTANCE == null) {
                synchronized(TokenDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        TokenDatabase::class.java, "token_database")
                        .build()
                }
            }
            return INSTANCE as TokenDatabase
        }
    }

}

