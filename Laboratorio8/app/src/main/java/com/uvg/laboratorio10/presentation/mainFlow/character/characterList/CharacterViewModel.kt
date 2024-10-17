package com.uvg.laboratorio10.presentation.mainFlow.character.characterList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.uvg.laboratorio10.data.source.CharacterDb
import com.uvg.laboratorio10.data.source.LocationDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//Se genera el ViewModel
class CharacterViewModel(
    savedStateHandle: SavedStateHandle //mantener los datos en el ciclo de vida de la app
): ViewModel() {
    private val characterDb = CharacterDb() //Jalo la base de datos de los personajes

    //_uiState recibe constantemente el flujo del estado
    private val _uiState: MutableStateFlow<CharacterState> = MutableStateFlow(
        CharacterState()
    ) //está constantemente actualizandose


    val uiState = _uiState.asStateFlow() //este es con el que trabajamos con Compose

    //Se recibe la lista de los personajes
    fun getListCharacters(){
        //Corrutina para obtener los datos
        viewModelScope.launch {
            //Nos aseguramos de actualizar los datos como se espera
            _uiState.update { state ->
                state.copy( //copiar lo que se tenga en el _uiState
                    isLoading = true //se coloca la pantalla de isLoading
                )
            }

            //Tiempo de espera antes de mandar la información (4 segundos)
            delay(4000)


            //Jalamos la información que se necesita (todos los personajes)
            val characters = characterDb.getAllCharacters()

            //Se establece el estado de isLoading a false, para que se muestre la información
            //actualizar el state con la data (lista)
            _uiState.update { state ->
                state.copy(
                    data = characters,
                    isLoading = false, //se desactiva la pantalla de isLoading
                )
            }
        }
    }

    //Métodos de manejo para la lógica de las pantallas de carga y error
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
        getListCharacters() //Para el onRetry se vuelve a probar la función de getListCharacters
    }
}