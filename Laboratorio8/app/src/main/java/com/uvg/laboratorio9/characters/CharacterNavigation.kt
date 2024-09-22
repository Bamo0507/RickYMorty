package com.uvg.laboratorio9.characters

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object CharacterDestination

fun NavGraphBuilder.characterScreen(
    onCharacterClick: (Int) -> Unit,
    navController: NavController
) {
    composable<CharacterDestination> {
        CharacterRoute(
            onCharacterClick = onCharacterClick,
            modifier = Modifier.fillMaxWidth(),
            navController = navController
        )
    }
}
