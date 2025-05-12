package com.uvg.laboratorio10.presentation.mainFlow.character.characterDetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

//Objeto serializable - Se maneja como class porque recibe un ID de la pantalla anterior
@Serializable
data class CharacterDetailDestination(
    val characterId: Int
)


//Se establece que
fun NavController.navigateToCharacterDetailScreen(
    destination: CharacterDetailDestination,
    navOptions: NavOptions? = null
){
    this.navigate(
        destination,
        navOptions
    )
}

fun NavGraphBuilder.characterDetailScreen(onNavigateBack: () -> Unit){
    composable<CharacterDetailDestination> { backStackEntry ->
        val destination: CharacterDetailDestination = backStackEntry.toRoute()
        CharacterDetailRoute(
            characterId = destination.characterId,
            onNavigateBack = onNavigateBack
        )
    }
}