package com.uvg.laboratorio10.presentation.mainFlow.character.characterDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.uvg.laboratorio10.data.repository.CharacterRepository
import com.uvg.laboratorio10.data.source.LocationDb
import com.uvg.laboratorio10.presentation.mainFlow.location.locationDetails.LocationDetailDestination
import com.uvg.laboratorio10.presentation.mainFlow.location.locationDetails.LocationDetailsState
import kotlinx.coroutines.delay
import com.uvg.laboratorio10.data.model.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(
    private val characterRepository: CharacterRepository,
    savedStateHandle: SavedStateHandle //Guardamos los datos en el ciclo de vida de la app
): ViewModel() {
    private val characterProfile = savedStateHandle.toRoute<CharacterDetailDestination>() //Ayuda a indicar la pantalla presente
    private val characterId = characterProfile.characterId

    private val _uiState: MutableStateFlow<CharacterDetailsState> = MutableStateFlow(
        CharacterDetailsState()
    ) //está constantemente actualizandose - de esta no se entera compose

    val uiState = _uiState.asStateFlow() //a esta se accede cuando se necesite

    //Recopilar información de personaje
    fun getCharacterData() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    isLoading = true
                )
            }

            delay(2000) // Si deseas mantener el delay

            try {
                val characterEntity = characterRepository.getCharacterById(characterId)

                if (characterEntity != null) {
                    val character = Character(
                        id = characterEntity.id,
                        name = characterEntity.name,
                        status = characterEntity.status,
                        species = characterEntity.species,
                        gender = characterEntity.gender,
                        image = characterEntity.image
                    )
                    _uiState.update { state ->
                        state.copy(
                            data = character,
                            isLoading = false,
                            hasError = false
                        )
                    }
                } else {
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            hasError = true
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        hasError = true
                    )
                }
            }
        }
    }


    //Métodos para la pantalla de carga y error
    fun onLoadingClick() {
        _uiState.update { state ->
            state.copy(
                isLoading = false, //desactivamos pantalla de carga
                hasError = true //hasError se activa para que se muestre la pantalla de error
            )
        }
    }
    fun onRetryClick() {
        _uiState.update { state ->
            state.copy(
                isLoading = true, //se vuelve a colocar la pantalla de carga
                hasError = false //seteamos el error a falso otra vez
            )
        }
        getCharacterData() //Volvemos a llamar al método de getCharacterData
    }
}