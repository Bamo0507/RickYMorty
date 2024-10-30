package com.uvg.laboratorio10.data.network.dto

import com.uvg.laboratorio10.data.entities.LocationEntity
import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
)

//Función para mapear lo que venga como una LocationEntity
fun LocationDto.mapToLocationModel(): LocationEntity{
    return LocationEntity(
        id = id,
        name = name,
        type = type,
        dimension = dimension
    )
}