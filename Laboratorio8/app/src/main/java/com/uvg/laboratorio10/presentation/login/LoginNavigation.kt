package com.uvg.laboratorio10.presentation.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.uvg.laboratorio10.domain.UserPreferences
import kotlinx.serialization.Serializable

@Serializable
data object LoginDestination //Objeto serializable con el que identificaré a mi pantalla de login


//Método del navgraphbuilder para pasar parámetros necesarios en la navegación
fun NavGraphBuilder.loginScreen(
    onLoginClick: () -> Unit,
    preferences: UserPreferences
){
    composable<LoginDestination>{
        LoginRoute(
            onLoginClick = onLoginClick,
            modifier = Modifier.fillMaxWidth(),
            preferences = preferences
        )
    }
}
