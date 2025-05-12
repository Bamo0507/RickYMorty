package com.uvg.laboratorio10.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uvg.laboratorio10.data.dao.CharacterDao
import com.uvg.laboratorio10.data.dao.LocationDao
import com.uvg.laboratorio10.data.entities.CharacterEntity
import com.uvg.laboratorio10.data.entities.LocationEntity

@Database(entities = [CharacterEntity::class, LocationEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun locationDao(): LocationDao
}
