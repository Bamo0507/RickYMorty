package com.uvg.laboratorio10.data.repository

import android.content.Context
import com.uvg.laboratorio10.data.dao.CharacterDao
import com.uvg.laboratorio10.data.entities.CharacterEntity
import com.uvg.laboratorio10.data.network.HttpClientProvider
import com.uvg.laboratorio10.data.network.RickAndMortyApi
import com.uvg.laboratorio10.data.network.dto.mapToCharacterModel
import com.uvg.laboratorio10.di.Dependencies

class CharacterRepository(context: Context) {

    private val characterDao: CharacterDao = Dependencies.provideDatabase(context).characterDao()
    //PASAR EL API
    private val api = RickAndMortyApi(HttpClientProvider.httpClient)

    suspend fun insertCharacters(characters: List<CharacterEntity>) {
        characterDao.insertAll(characters)
    }

    suspend fun getAllCharacters(): List<CharacterEntity> {
        return characterDao.getAllCharacters()
    }

    suspend fun getCharacterById(id: Int): CharacterEntity? {
        return characterDao.getCharacterById(id)
    }

    //Suspend Function para jalar los personajes de la API
    suspend fun getAllCharactersFromApi(): List<CharacterEntity> {
        val response = api.getCharacters()
        return response.results.map { it.mapToCharacterModel() }
    }

}
