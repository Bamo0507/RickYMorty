package com.uvg.laboratorio10.presentation.mainFlow.location.locationDetails

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.uvg.laboratorio10.data.repository.LocationRepository

class LocationDetailsViewModelFactory(
    private val context: Context,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle?
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String, modelClass: Class<T>, handle: SavedStateHandle
    ): T {
        val repository = LocationRepository(context)
        return LocationDetailsViewModel(repository, handle) as T
    }
}
