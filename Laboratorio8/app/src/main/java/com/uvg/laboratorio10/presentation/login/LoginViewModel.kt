package com.uvg.laboratorio10.presentation.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.uvg.laboratorio10.domain.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val preferences: UserPreferences,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    //Estado interno que no es accesible directamente desde la UI
    private val _uiState = MutableStateFlow<LoginState>(LoginState())
    val uiState: StateFlow<LoginState> = _uiState

    // Estado para indicar si el login fue exitoso
    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

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

    private fun loginUser(){
        viewModelScope.launch {
            preferences.setUserName(_uiState.value.name)
            preferences.logIn()
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