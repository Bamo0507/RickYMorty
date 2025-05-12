package com.uvg.laboratorio10.presentation.mainFlow.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.laboratorio10.domain.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val preferences: UserPreferences
): ViewModel() {
    //Manejar lo que ocurre en la pantalla en su ciclo de vida
    private val _uiState = MutableStateFlow<ProfileState>(ProfileState())
    val uiState: StateFlow<ProfileState> = _uiState

    //Al iniciar la pantalla se cargará el nombre usuario
    init {
        loadUserName()
    }

    //Función para cargar el nombre de usuario
    private fun loadUserName(){
        viewModelScope.launch {
            val userName = preferences.getUserName() ?: ""
            _uiState.value = _uiState.value.copy(userName = userName, isLoading = false)
        }
    }

    // Función para cerrar sesión y actualizar UserPreferences
    fun logOut(){
        viewModelScope.launch {
            preferences.logOut()
            _uiState.value = _uiState.value.copy(isLoading = true)
        }
    }


}