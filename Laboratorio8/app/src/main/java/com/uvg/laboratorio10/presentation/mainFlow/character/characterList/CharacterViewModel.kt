package com.uvg.laboratorio10.presentation.mainFlow.character.characterList

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.laboratorio10.data.repository.CharacterRepository
import com.uvg.laboratorio10.data.entities.CharacterEntity
import com.uvg.laboratorio10.data.model.Character
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val characterRepository: CharacterRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState: MutableStateFlow<CharacterState> = MutableStateFlow(
        CharacterState()
    )

    val uiState = _uiState.asStateFlow()

    fun getListCharacters() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    isLoading = true
                )
            }

            delay(4000)

            try {
                // Intentar obtener los personajes del API
                Log.d("CharacterViewModel", "Intentando obtener personajes del API")
                val characterEntities = characterRepository.getAllCharactersFromApi()
                Log.d("CharacterViewModel", "Datos obtenidos del API exitosamente")

                // Insertar en la base de datos local
                characterRepository.insertCharacters(characterEntities)

                // Mapear a modelo de dominio
                val characters = characterEntities.map {
                    Character(
                        id = it.id,
                        name = it.name,
                        status = it.status,
                        species = it.species,
                        gender = it.gender,
                        image = it.image
                    )
                }

                _uiState.update { state ->
                    state.copy(
                        data = characters,
                        isLoading = false,
                        hasError = false
                    )
                }
            } catch (e: Exception) {
                // Si falla, obtener de la base de datos local
                Log.e("CharacterViewModel", "Error al obtener datos del API", e)
                val characterEntities = characterRepository.getAllCharacters()

                if (characterEntities.isNotEmpty()) {
                    val characters = characterEntities.map {
                        Log.d("CharacterViewModel", "Obteniendo personajes de la base de datos local")
                        Character(
                            id = it.id,
                            name = it.name,
                            status = it.status,
                            species = it.species,
                            gender = it.gender,
                            image = it.image
                        )
                    }

                    _uiState.update { state ->
                        state.copy(
                            data = characters,
                            isLoading = false,
                            hasError = false
                        )
                    }
                } else {
                    // Si no hay datos locales, mostrar error
                    //Debería ocurrir solo si nunca se cargo la aplicación con internet
                    Log.d("CharacterViewModel", "No hay datos en la base de datos local")

                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            hasError = true
                        )
                    }
                }
            }
        }
    }

    fun onLoadingClick() {
        _uiState.update { state ->
            state.copy(
                isLoading = false,
                hasError = true
            )
        }
    }

    fun onRetryClick() {
        _uiState.update { state ->
            state.copy(
                isLoading = true,
                hasError = false
            )
        }
        getListCharacters()
    }
}
