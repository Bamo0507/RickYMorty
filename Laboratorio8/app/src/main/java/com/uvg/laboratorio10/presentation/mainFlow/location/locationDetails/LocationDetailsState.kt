package com.uvg.laboratorio10.presentation.mainFlow.location.locationDetails

import com.uvg.laboratorio10.data.model.Location

data class LocationDetailsState(
    val data: Location? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false
)

