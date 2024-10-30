package com.uvg.laboratorio10.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationsResponseDto(
    val info: InfoDto,
    val results: List<LocationDto>
)
