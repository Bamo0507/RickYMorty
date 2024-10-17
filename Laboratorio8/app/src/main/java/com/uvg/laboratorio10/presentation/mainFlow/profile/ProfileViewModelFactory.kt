package com.uvg.laboratorio10.presentation.mainFlow.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uvg.laboratorio10.domain.UserPreferences

class ProfileViewModelFactory(
    private val preferences: UserPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(preferences) as T
    }
}
