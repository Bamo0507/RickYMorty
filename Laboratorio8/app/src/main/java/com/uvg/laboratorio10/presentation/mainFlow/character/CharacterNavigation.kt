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


//Objeto serializable que sirve como la guía para esta nested navigation
@Serializable
data object CharacterNavGraph

//Método del navgraphbuilder para mandar a llamar a todo lo que se necesita bajo este flujo
fun NavGraphBuilder.characterGraph(
    navController: NavController
){
    //Nested Navigation de Personajes
    navigation<CharacterNavGraph>(startDestination = CharacterDestination){
        //Pantalla de lista de personajes
        //Lógica para el movimiento hacia la siguiente pantalla
        //NAVGRAPHBUILDER METHOD DE LA PANTALLA CON LA LISTA
        characterScreen(
            onCharacterClick = { character ->
                navController.navigateToCharacterDetailScreen( //se manda a llamar al método definido para poder pasar el ID
                    destination = CharacterDetailDestination(
                        characterId = character
                    )
                )
            }

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