package com.uvg.laboratorio10.presentation.login

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.uvg.laboratorio10.data.entities.CharacterEntity
import com.uvg.laboratorio10.data.entities.LocationEntity
import com.uvg.laboratorio10.domain.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import com.uvg.laboratorio10.data.repository.CharacterRepository
import com.uvg.laboratorio10.data.repository.LocationRepository
import com.uvg.laboratorio10.data.source.CharacterDb
import com.uvg.laboratorio10.data.source.LocationDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(
    private val characterRepository: CharacterRepository,
    private val locationRepository: LocationRepository,
    private val preferences: UserPreferences,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    //Estado interno que no es accesible directamente desde la UI
    private val _uiState = MutableStateFlow<LoginState>(LoginState())
    val uiState: StateFlow<LoginState> = _uiState

    // Estado para indicar si el login fue exitoso
    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    private suspend fun synchronizeData() {
        _uiState.update { it.copy(isSynchronizing = true) }

        delay(2000) // Simular un tiempo de espera

        try {
            // Obtener los datos de CharacterDb y LocationDb
            val characters = CharacterDb().getAllCharacters()
            val locations = LocationDb().getAllLocations()

            Log.d("LoginViewModel", "Obtenidos ${characters.size} personajes y ${locations.size} ubicaciones.")

            // Mapear a entidades de Room
            val characterEntities = characters.map {
                CharacterEntity(
                    id = it.id,
                    name = it.name,
                    status = it.status,
                    species = it.species,
                    gender = it.gender,
                    image = it.image
                )
            }

            val locationEntities = locations.map {
                LocationEntity(
                    id = it.id,
                    name = it.name,
                    type = it.type,
                    dimension = it.dimension
                )
            }

            // Insertar en la base de datos
            characterRepository.insertCharacters(characterEntities)
            locationRepository.insertLocations(locationEntities)

            Log.d("LoginViewModel", "Datos sincronizados e insertados en la base de datos.")

        } catch (e: Exception) {
            Log.e("LoginViewModel", "Error al sincronizar datos: ${e.message}")
        } finally {
            _uiState.update { it.copy(isSynchronizing = false) }
        }
    }


    //Método para Manejar los eventos
    fun onEvent(event: LoginScreenEvent){
        when(event){
            is LoginScreenEvent.UserNameChange -> onUserChanged(event.name)
            LoginScreenEvent.LoginClick -> onLogIn()
        }
    }

    //Funciones para los eventos
    //-----------------login----------------
    // Evento de login que verifica si el nombre no está vacío
    private fun onLogIn() {
        if (!_uiState.value.isEmpty) {
            loginUser() // Ejecuta la lógica de inicio de sesión
        }
    }

    private fun loginUser() {
        viewModelScope.launch {
            preferences.setUserName(_uiState.value.name)
            synchronizeData()
            _loginSuccess.value = true
        }
    }

    //------------------user changed---------------------
    private fun onUserChanged(name: String){
        _uiState.update { it.copy(
            name = name,
            isEmpty = name.isEmpty()
        )}
    }

}