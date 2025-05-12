package com.uvg.laboratorio10.presentation.mainFlow.character.characterList

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

//Se define el objeto serializable para la navegación (se deja como object porque no recibe parámetros)
@Serializable
data object CharacterDestination

//Método para la navegación
fun NavGraphBuilder.characterScreen(
    onCharacterClick: (Int) -> Unit,
) {
    composable<CharacterDestination> {
        CharacterRoute(
            onCharacterClick = onCharacterClick, //Pasamos el método de navegación
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
