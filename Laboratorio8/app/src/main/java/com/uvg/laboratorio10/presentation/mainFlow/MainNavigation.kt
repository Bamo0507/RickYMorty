package com.uvg.laboratorio10.presentation.mainFlow

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uvg.laboratorio10.domain.UserPreferences
import kotlinx.serialization.Serializable

@Serializable
data object MainNavigationGraph

fun NavController.navigateToMainGraph(navOptions: NavOptions? = null){
    this.navigate(MainNavigationGraph, navOptions)
}

fun NavGraphBuilder.mainNavigationGraph(
    onLogoutClick: () -> Unit,
    preferences: UserPreferences
){
    composable<MainNavigationGraph> {
        val nestedNavController = rememberNavController()
        MainFlowScreen(
            navController = nestedNavController,
            onLogoutClick = onLogoutClick,
            preferences = preferences
        )
    }
}
