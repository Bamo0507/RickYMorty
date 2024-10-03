package com.uvg.laboratorio10.presentation.mainFlow.location.locationDetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class LocationDetailDestination(
    val locationId: Int
)

fun NavController.navigateToLocationDetailScreen(
    locationId: Int,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = LocationDetailDestination(locationId = locationId),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.locationDetailScreen(
    onNavigateBack: () -> Unit
) {
    composable<LocationDetailDestination> { backStackEntry ->
        val destination: LocationDetailDestination = backStackEntry.toRoute()
        LocationDetailsRoute(
            onNavigateBack = onNavigateBack
        )
    }
}
