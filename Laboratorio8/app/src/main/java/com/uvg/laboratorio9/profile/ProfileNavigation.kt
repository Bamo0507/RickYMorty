package com.uvg.laboratorio9.profile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ProfileDestination

fun NavGraphBuilder.profileScreen(
    onLogoutClick: () -> Unit,
    navController: NavController
){
    composable<ProfileDestination> {
        ProfileRoute(
            onLogoutClick = onLogoutClick,
            modifier = Modifier.fillMaxWidth(),
            navController = navController
        )
    }
}