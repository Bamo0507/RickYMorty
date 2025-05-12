package com.uvg.laboratorio10.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class CharacterLocationDto(
    val name: String,
    val url: String
)