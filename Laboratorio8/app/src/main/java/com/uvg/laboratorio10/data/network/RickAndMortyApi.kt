package com.uvg.laboratorio10.data.network

import com.uvg.laboratorio10.data.network.dto.CharactersResponseDto
import com.uvg.laboratorio10.data.network.dto.LocationsResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class RickAndMortyApi(private val client: HttpClient) {

    suspend fun getCharacters(): CharactersResponseDto {
        return client.get("https://rickandmortyapi.com/api/character").body()
    }

    suspend fun getLocations(): LocationsResponseDto {
        return client.get("https://rickandmortyapi.com/api/location").body()
    }
}