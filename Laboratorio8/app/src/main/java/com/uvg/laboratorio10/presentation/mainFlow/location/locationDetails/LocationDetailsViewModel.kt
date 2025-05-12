package com.uvg.laboratorio10.presentation.mainFlow.location.locationDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.laboratorio10.data.repository.LocationRepository
import androidx.navigation.toRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.uvg.laboratorio10.data.model.Location


class LocationDetailsViewModel(
    private val locationRepository: LocationRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val locationProfile = savedStateHandle.toRoute<LocationDetailDestination>()
    private val locationId = locationProfile.locationId

    private val _uiState: MutableStateFlow<LocationDetailsState> = MutableStateFlow(LocationDetailsState()) //está constantemente actualizandose
    val uiState = _uiState.asStateFlow() //se deja pública compose no debe tener acceso

    fun getLocationData() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    isLoading = true
                )
            }

            delay(2000)

            try {
                val locationEntity = locationRepository.getLocationById(locationId)
                if (locationEntity != null) {
                    val location = Location(
                        id = locationEntity.id,
                        name = locationEntity.name,
                        type = locationEntity.type,
                        dimension = locationEntity.dimension
                    )
                    _uiState.update { state ->
                        state.copy(
                            data = location,
                            isLoading = false,
                            hasError = false
                        )
                    }
                } else {
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            hasError = true
                        )
                    }
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
        getLocationData() // Reintentar obtener los datos
    }
}