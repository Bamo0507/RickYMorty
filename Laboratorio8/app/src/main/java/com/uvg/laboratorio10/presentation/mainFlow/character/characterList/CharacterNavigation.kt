package com.uvg.laboratorio10.presentation.mainFlow.character.characterList

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object CharacterDestination

fun NavGraphBuilder.characterScreen(
    onCharacterClick: (Int) -> Unit,
) {
    composable<CharacterDestination> {
        CharacterRoute(
            onCharacterClick = onCharacterClick,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
