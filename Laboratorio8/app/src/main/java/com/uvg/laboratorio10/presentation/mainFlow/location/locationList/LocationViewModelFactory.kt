package com.uvg.laboratorio10.presentation.mainFlow.location.locationList

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.uvg.laboratorio10.data.repository.LocationRepository

class LocationViewModelFactory(
    private val context: Context,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String, modelClass: Class<T>, handle: SavedStateHandle
    ): T {
        val repository = LocationRepository(context)
        return LocationViewModel(repository, handle) as T
    }
}
