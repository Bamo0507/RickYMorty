package com.uvg.laboratorio8.characters

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object CharacterDestination

fun NavGraphBuilder.characterScreen(
    onCharacterClick: (Int) -> Unit,
    onBackToLogin: () -> Unit // Callback para manejar el botón atrás
) {
    composable<CharacterDestination> {
        CharacterRoute(
            onCharacterClick = onCharacterClick,
            modifier = Modifier.fillMaxWidth()
        )

        // Intercepta el botón "atrás" para cerrar la aplicación
        BackHandler {
            onBackToLogin() // Llama a la función que cierra la aplicación
        }
    }
}
