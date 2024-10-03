package com.uvg.laboratorio10.presentation.mainFlow.character

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.uvg.laboratorio10.presentation.mainFlow.character.characterDetails.CharacterDetailDestination
import com.uvg.laboratorio10.presentation.mainFlow.character.characterDetails.characterDetailScreen
import com.uvg.laboratorio10.presentation.mainFlow.character.characterDetails.navigateToCharacterDetailScreen
import com.uvg.laboratorio10.presentation.mainFlow.character.characterList.CharacterDestination
import com.uvg.laboratorio10.presentation.mainFlow.character.characterList.characterScreen
import kotlinx.serialization.Serializable

@Serializable
data object CharacterNavGraph

fun NavGraphBuilder.characterGraph(
    navController: NavController
){
    //Nested Navigation de Personajes
    navigation<CharacterNavGraph>(startDestination = CharacterDestination){
        //Pantalla de lista de personajes
        //Lógica para el movimiento hacia la siguiente pantalla
        characterScreen(
            onCharacterClick = { character ->
                navController.navigateToCharacterDetailScreen(
                    destination = CharacterDetailDestination(
                        characterId = character
                    )
                )
            }
            //Preguntar a Durini si se debe quitar la imlpementación de
            //navegación de la última vez al regresar de characterScreen
        )
        //Pantalla de detalle de personaje
        //Lógica de navegación de esta pantalla
        //Solo se regresa :)
        characterDetailScreen(
            onNavigateBack = {
                navController.navigateUp()
            }
        )
    }
}