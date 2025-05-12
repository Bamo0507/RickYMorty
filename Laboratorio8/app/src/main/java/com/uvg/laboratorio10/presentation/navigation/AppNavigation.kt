package com.uvg.laboratorio10.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.uvg.laboratorio10.presentation.SplashDestination
import com.uvg.laboratorio10.presentation.login.LoginDestination
import com.uvg.laboratorio10.presentation.login.loginScreen
import com.uvg.laboratorio10.presentation.mainFlow.mainNavigationGraph
import com.uvg.laboratorio10.presentation.splashScreen
import com.uvg.laboratorio10.domain.UserPreferences
import com.uvg.laboratorio10.presentation.mainFlow.MainNavigationGraph

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    userPreferences: UserPreferences
){
    NavHost(
        navController = navController,
        startDestination = SplashDestination,
        modifier = modifier
    ){
        splashScreen(
            navController = navController,
            preferences = userPreferences
        )

        loginScreen(
            onLoginClick = {
                navController.navigate(MainNavigationGraph) {
                    popUpTo(LoginDestination) {
                        inclusive = true
                    }
                }
            },
            preferences = userPreferences
        )

        mainNavigationGraph(
            onLogoutClick = {
                navController.navigate(LoginDestination) {
                    popUpTo(0)
                }
            },
            preferences = userPreferences
        )
    }
}
