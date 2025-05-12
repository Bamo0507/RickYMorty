package com.uvg.laboratorio10.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.laboratorio10.domain.UserPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SplashViewModel(
    private val preferences: UserPreferences
) : ViewModel() {

    val isLoggedIn = preferences.loggedStatus()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)
}
