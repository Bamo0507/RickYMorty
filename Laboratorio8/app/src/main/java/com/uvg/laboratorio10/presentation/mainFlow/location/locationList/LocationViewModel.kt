package com.uvg.laboratorio10.presentation.mainFlow.location.locationList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.uvg.laboratorio10.data.repository.LocationRepository
import com.uvg.laboratorio10.data.model.Location
import com.uvg.laboratorio10.presentation.mainFlow.location.locationDetails.LocationDetailDestination
import com.uvg.laboratorio10.presentation.mainFlow.location.locationDetails.LocationDetailsState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationViewModel(
    private val locationRepository: LocationRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiState: MutableStateFlow<LocationState> = MutableStateFlow(
        LocationState()
    ) //está constantemente actualizandose
    val uiState = _uiState.asStateFlow() //se deja pública compose no debe tener acceso

    fun getListLocations() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    isLoading = true
                )
            }

            delay(4000)

            try {
                val locationEntities = locationRepository.getAllLocations()
                val locations = locationEntities.map {
                    Location(
                        id = it.id,
                        name = it.name,
                        type = it.type,
                        dimension = it.dimension
                    )
                }
                _uiState.update { state ->
                    state.copy(
                        data = locations,
                        isLoading = false,
                        hasError = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        hasError = true
                    )
                }
            }
        }
    }

    fun onLoadingClick() {
        _uiState.update { state ->
            state.copy(
                isLoading = false,
                hasError = true
            )
        }
    }
    fun onRetryClick() {
        _uiState.update { state ->
            state.copy(
                isLoading = true,
                hasError = false
            )
        }
        getListLocations()
    }
}