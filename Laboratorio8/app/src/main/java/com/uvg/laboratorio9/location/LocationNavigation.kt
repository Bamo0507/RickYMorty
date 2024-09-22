package com.uvg.laboratorio9.location

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object LocationDestination

fun NavGraphBuilder.locationScreen(
    onLocationClick: (Int) -> Unit,
    navController: NavController
) {
    composable<LocationDestination> {
        LocationRoute(
            onLocationClick = onLocationClick,
            modifier = Modifier.fillMaxWidth(),
            navController = navController
        )
    }
}