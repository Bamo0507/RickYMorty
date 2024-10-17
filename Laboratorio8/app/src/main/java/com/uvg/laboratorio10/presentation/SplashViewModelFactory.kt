package com.uvg.laboratorio10.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uvg.laboratorio10.domain.UserPreferences

class SplashViewModelFactory(
    private val preferences: UserPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SplashViewModel(preferences) as T
    }
}
