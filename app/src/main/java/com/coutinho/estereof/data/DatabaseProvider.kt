package com.coutinho.estereof.data

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "estereof_db"
                    ).fallbackToDestructiveMigration(false) // cuidado com isso em produção
                .build()
            INSTANCE = instance
            instance
        }
    }
}
