package com.uvg.laboratorio9.locationDetails

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
    destination: LocationDetailDestination,
    navOptions: NavOptions? = null
) {
    this.navigate(destination, navOptions)
}

fun NavGraphBuilder.locationDetailScreen(onNavigateBack: () -> Unit) {
    composable<LocationDetailDestination> { backStackEntry ->
        val destination: LocationDetailDestination = backStackEntry.toRoute()
        LocationDetailsRoute(
            locationId = destination.locationId,
            onNavigateBack = onNavigateBack
        )
    }
}
