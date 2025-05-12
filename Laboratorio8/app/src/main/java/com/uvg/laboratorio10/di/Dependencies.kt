package com.uvg.laboratorio10.di

import android.content.Context
import androidx.room.Room
import com.uvg.laboratorio10.data.database.AppDatabase

object Dependencies {
    private var database: AppDatabase? = null

    private fun buildDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database.db"
        ).build()
    }

    fun provideDatabase(context: Context): AppDatabase {
        return database ?: synchronized(this) {
            database ?: buildDatabase(context).also { database = it }
        }
    }
}
