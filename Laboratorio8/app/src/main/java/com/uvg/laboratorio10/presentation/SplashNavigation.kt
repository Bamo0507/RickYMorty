package com.uvg.laboratorio10.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import com.uvg.laboratorio10.domain.UserPreferences

@Serializable
data object SplashDestination

fun NavGraphBuilder.splashScreen(
    navController: NavController,
    preferences: UserPreferences
){
    composable<SplashDestination> {
        SplashRoute(
            navController = navController,
            preferences = preferences,
            modifier = Modifier.fillMaxSize()
        )
    }
}
