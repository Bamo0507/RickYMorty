package com.uvg.laboratorio10.data.repository

import android.content.Context
import com.uvg.laboratorio10.data.dao.LocationDao
import com.uvg.laboratorio10.data.entities.LocationEntity
import com.uvg.laboratorio10.di.Dependencies

class LocationRepository(context: Context) {

    private val locationDao: LocationDao = Dependencies.provideDatabase(context).locationDao()

    suspend fun insertLocations(locations: List<LocationEntity>) {
        locationDao.insertAll(locations)
    }

    suspend fun getAllLocations(): List<LocationEntity> {
        return locationDao.getAllLocations()
    }

    suspend fun getLocationById(id: Int): LocationEntity? {
        return locationDao.getLocationById(id)
    }
}
