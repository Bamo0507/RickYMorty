package com.uvg.laboratorio10.presentation.mainFlow.location.locationList

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object LocationDestination

fun NavGraphBuilder.locationScreen(
    onLocationClick: (Int) -> Unit,
) {
    composable<LocationDestination> {
        LocationRoute(
            onLocationClick = onLocationClick,
            modifier = Modifier.fillMaxWidth()
        )
    }
}