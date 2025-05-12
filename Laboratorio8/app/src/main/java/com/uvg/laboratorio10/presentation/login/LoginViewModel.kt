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
    private val preferences: UserPreferences,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Estado interno
    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState

    // Estado para indicar si el login fue exitoso
    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    // Manejar eventos
    fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.UserNameChange -> onUserChanged(event.name)
            LoginScreenEvent.LoginClick -> onLogIn()
        }
    }

    private fun onLogIn() {
        if (!_uiState.value.isEmpty) {
            viewModelScope.launch {
                preferences.setUserName(_uiState.value.name)
                preferences.logIn()
                _loginSuccess.value = true
            }
        }
    }

    private fun onUserChanged(name: String) {
        _uiState.update {
            it.copy(
                name = name,
                isEmpty = name.isEmpty()
            )
        }
    }
}
