package com.uvg.laboratorio10.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uvg.laboratorio10.data.entities.LocationEntity

@Dao
interface LocationDao {
    //Insertar todas las locaciones
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locations: List<LocationEntity>)

    //Obtener todas las locaciones
    @Query("SELECT * FROM locations")
    suspend fun getAllLocations(): List<LocationEntity>

    //Obtener una locacion por su id
    @Query("SELECT * FROM locations WHERE id = :id")
    suspend fun getLocationById(id: Int): LocationEntity?
}
