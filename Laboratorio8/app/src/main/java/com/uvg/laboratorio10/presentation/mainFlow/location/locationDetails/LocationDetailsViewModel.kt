package com.uvg.laboratorio10.presentation.mainFlow.location.locationDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.laboratorio10.data.source.LocationDb
import androidx.navigation.toRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LocationDetailsViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val locationDb = LocationDb() //lugar de donde saco el location esperado
    private val locationProfile = savedStateHandle.toRoute<LocationDetailDestination>() //Ayuda a indicar a donde se navega
    private val _uiState: MutableStateFlow<LocationDetailsState> = MutableStateFlow(LocationDetailsState()) //está constantemente actualizandose
    val uiState = _uiState.asStateFlow() //se deja pública compose no debe tener acceso

    fun getLocationData(){
        viewModelScope.launch {
            //Nos aseguramos de actualizar los datos como se espera
            _uiState.update { state ->
                state.copy(
                    isLoading = true
                )
            }

            //Tiempo de espera antes de mandar la información
            delay(2000)

            //Jalamos la información que se necesita
            val location = locationDb.getLocationById(
                locationProfile.locationId
            )

            //Se establece el estado de isLoading a false, para que se muestre la información
            _uiState.update { state ->
                state.copy(
                    data = location,
                    isLoading = false,
                )
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