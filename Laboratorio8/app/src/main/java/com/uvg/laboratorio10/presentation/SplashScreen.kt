package com.uvg.laboratorio10.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.uvg.laboratorio10.domain.UserPreferences
import com.uvg.laboratorio10.presentation.login.LoginDestination
import com.uvg.laboratorio10.presentation.mainFlow.MainNavigationGraph

@Composable
fun SplashRoute(
    navController: NavController,
    preferences: UserPreferences,
    modifier: Modifier = Modifier
){
    val viewModel: SplashViewModel = viewModel(
        factory = SplashViewModelFactory(preferences)
    )
    SplashScreen(navController = navController, viewModel = viewModel, modifier = modifier)
}

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel,
    modifier: Modifier = Modifier
) {
    // Observar el estado de isLoggedIn desde el ViewModel
    val isLoggedIn by viewModel.isLoggedIn.collectAsState(initial = null)

    // Navegar según el estado de isLoggedIn
    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn != null) {
            if (isLoggedIn == true) {
                navController.navigate(MainNavigationGraph) {
                    popUpTo(SplashDestination) {
                        inclusive = true
                    }
                }
            } else {
                navController.navigate(LoginDestination) {
                    popUpTo(SplashDestination) {
                        inclusive = true
                    }
                }
            }
        }
    }

    // Mostrar el indicador de carga
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text("Verificando login...")
        }
    }
}
