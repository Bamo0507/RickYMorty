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
    savedStateHandle: SavedStateHandle //Guardamos los datos en el ciclo de vida de la app
): ViewModel() {

    private val characterDb = CharacterDb() //Recopilamos la "base de datos" de characters

    private val characterProfile = savedStateHandle.toRoute<CharacterDetailDestination>() //Ayuda a indicar la pantalla presente

    private val _uiState: MutableStateFlow<CharacterDetailsState> = MutableStateFlow(
        CharacterDetailsState()
    ) //está constantemente actualizandose - de esta no se entera compose

    val uiState = _uiState.asStateFlow() //a esta se accede cuando se necesite

    //Recopilar información de personaje
    fun getCharacterData(){
        //Se lanza el efecto de corrutina para obtener los datos del personaje (tener una corrutina sin el suspend)
        //el viewmodelscope evita que ocurran filtraciones de memoria - launch - lanza una corrutina
        viewModelScope.launch {
            //Nos aseguramos de actualizar los datos como se espera
            _uiState.update { state ->
                //state.copy genera una copia del estado actual del _uiState (el que lleva control de absolutamente todo)
                state.copy(
                    isLoading = true //activamos pantalla de carga
                )
            }

            //Tiempo de espera antes de mandar la información - simulación de espera
            delay(2000)

            //Jalamos la información que se necesita
            val character = characterDb.getCharacterById(
                characterProfile.characterId //En la pantalla se registra el ID y acá lo mandamos a llamar (está en la info de la class)
            )

            //Se establece el estado de isLoading a false, para que se muestre la información (se quita pantalla de loading)
            _uiState.update { state ->
                state.copy(
                    data = character,
                    isLoading = false, //desactivamos pantalla de carga
                )
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