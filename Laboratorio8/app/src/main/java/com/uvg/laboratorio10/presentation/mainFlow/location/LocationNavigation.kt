package com.uvg.laboratorio10.presentation.mainFlow.location

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.uvg.laboratorio10.presentation.mainFlow.location.locationDetails.locationDetailScreen
import com.uvg.laboratorio10.presentation.mainFlow.location.locationDetails.navigateToLocationDetailScreen
import com.uvg.laboratorio10.presentation.mainFlow.location.locationList.LocationDestination
import com.uvg.laboratorio10.presentation.mainFlow.location.locationList.locationScreen
import kotlinx.serialization.Serializable

@Serializable
data object LocationsNavGraph

fun NavController.navigateToLocationsGraph(navOptions: NavOptions? = null) {
    this.navigate(LocationsNavGraph, navOptions)
}

fun NavGraphBuilder.locationsGraph(
    navController: NavController
) {
    navigation<LocationsNavGraph>(
        startDestination = LocationDestination
    ) {
        locationScreen(
            onLocationClick = navController::navigateToLocationDetailScreen
        )
        locationDetailScreen(
            onNavigateBack = navController::navigateUp
        )
    }
}