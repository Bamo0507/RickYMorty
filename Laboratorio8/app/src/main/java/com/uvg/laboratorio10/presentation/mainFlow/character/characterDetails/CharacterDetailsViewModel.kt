package com.uvg.laboratorio10.presentation.mainFlow.character.characterDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.uvg.laboratorio10.data.source.CharacterDb
import com.uvg.laboratorio10.data.source.LocationDb
import com.uvg.laboratorio10.presentation.mainFlow.location.locationDetails.LocationDetailDestination
import com.uvg.laboratorio10.presentation.mainFlow.location.locationDetails.LocationDetailsState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val characterDb = CharacterDb() //lugar de donde saco el location esperado
    private val characterProfile = savedStateHandle.toRoute<CharacterDetailDestination>() //Ayuda a indicar a donde se navega
    private val _uiState: MutableStateFlow<CharacterDetailsState> = MutableStateFlow(
        CharacterDetailsState()
    ) //está constantemente actualizandose
    val uiState = _uiState.asStateFlow() //se deja pública compose no debe tener acceso

    fun getCharacterData(){
        viewModelScope.launch {
            //Nos aseguramos de actualizar los datos como se espera
            _uiState.update { state ->
                state.copy(
                    isLoading = true
                )
            }

            //Tiempo de espera antes de mandar la información
            delay(2000)

            //Jalamos la información que se necesita
            val character = characterDb.getCharacterById(
                characterProfile.characterId
            )

            //Se establece el estado de isLoading a false, para que se muestre la información
            _uiState.update { state ->
                state.copy(
                    data = character,
                    isLoading = false,
                )
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
        getCharacterData() // Reintentar obtener los datos
    }
}