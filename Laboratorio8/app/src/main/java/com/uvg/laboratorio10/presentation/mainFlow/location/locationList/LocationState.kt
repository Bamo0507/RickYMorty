package com.uvg.laboratorio10.presentation.mainFlow.location.locationList

import com.uvg.laboratorio10.data.model.Location

data class LocationState (
    val data: List<Location> = emptyList(),
    val isLoading: Boolean = false,
    val hasError: Boolean = false
    )