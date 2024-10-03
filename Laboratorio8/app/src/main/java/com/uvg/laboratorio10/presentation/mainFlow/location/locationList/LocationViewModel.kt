package com.uvg.laboratorio10.presentation.mainFlow.location.locationList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.uvg.laboratorio10.data.source.LocationDb
import com.uvg.laboratorio10.presentation.mainFlow.location.locationDetails.LocationDetailDestination
import com.uvg.laboratorio10.presentation.mainFlow.location.locationDetails.LocationDetailsState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val locationDb = LocationDb() //lugar de donde saco el location esperado
    private val _uiState: MutableStateFlow<LocationState> = MutableStateFlow(
        LocationState()
    ) //está constantemente actualizandose
    val uiState = _uiState.asStateFlow() //se deja pública compose no debe tener acceso

    fun getListLocations(){
        viewModelScope.launch {
            //Nos aseguramos de actualizar los datos como se espera
            _uiState.update { state ->
                state.copy(
                    isLoading = true
                )
            }

            //Tiempo de espera antes de mandar la información
            delay(4000)

            //Jalamos la información que se necesita
            val locations = locationDb.getAllLocations()

            //Se establece el estado de isLoading a false, para que se muestre la información
            _uiState.update { state ->
                state.copy(
                    data = locations,
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
        getListLocations()
    }
}