package com.uvg.laboratorio10.presentation.login

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.uvg.laboratorio10.data.repository.CharacterRepository
import com.uvg.laboratorio10.data.repository.LocationRepository
import com.uvg.laboratorio10.domain.UserPreferences

class LoginViewModelFactory(
    private val context: Context,
    private val preferences: UserPreferences,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String, modelClass: Class<T>, handle: SavedStateHandle
    ): T {
        return LoginViewModel(preferences, handle) as T
    }
}
