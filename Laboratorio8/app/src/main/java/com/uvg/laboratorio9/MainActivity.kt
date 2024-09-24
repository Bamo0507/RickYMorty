package com.uvg.laboratorio9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.uvg.laboratorio9.characterDetails.CharacterDetailDestination
import com.uvg.laboratorio9.characterDetails.characterDetailScreen
import com.uvg.laboratorio9.characterDetails.navigateToCharacterDetailScreen
import com.uvg.laboratorio9.ui.theme.Laboratorio9Theme
import com.uvg.laboratorio9.login.LoginDestination
import com.uvg.laboratorio9.login.loginScreen
import com.uvg.laboratorio9.characters.characterScreen
import com.uvg.laboratorio9.characters.CharacterDestination
import com.uvg.laboratorio9.components.BottomNavigationBar
import com.uvg.laboratorio9.location.LocationDestination
import com.uvg.laboratorio9.location.locationScreen
import com.uvg.laboratorio9.locationDetails.LocationDetailDestination
import com.uvg.laboratorio9.locationDetails.locationDetailScreen
import com.uvg.laboratorio9.locationDetails.navigateToLocationDetailScreen
import com.uvg.laboratorio9.profile.ProfileDestination
import com.uvg.laboratorio9.profile.profileScreen
import kotlinx.serialization.Serializable

/////////////////////////////////////
//2S para la nested navigation
@Serializable
data object Character

@Serializable
data object Location
/////////////////////////////////////

val topLevelDestinations = listOf(
    CharacterDestination::class,
    LocationDestination::class,
    ProfileDestination::class
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Laboratorio9Theme {
                val navController = rememberNavController()
                var bottomBarVisible by rememberSaveable {
                    mutableStateOf(false)
                }

                val currentDestination = navController.currentBackStackEntryAsState().value?.destination

                // Extraer solo la parte de la ruta después de 'route='
                val routeString = currentDestination?.route?.substringAfter("route=")

                bottomBarVisible = if (routeString != null) {
                    // Comparamos el routeString con el nombre calificado (qualified name) de los data objects
                    val isTopLevel = topLevelDestinations.any { destination ->
                        val destinationString = destination.javaObjectType.toString().substringAfter("class ")
                        val isMatching = (routeString == destinationString)
                        isMatching
                    }
                    isTopLevel
                } else {
                    false
                }


                Scaffold(
                    bottomBar = {
                        // Solo mostramos la barra de navegación si es necesario
                        AnimatedVisibility(visible = bottomBarVisible) {
                            BottomNavigationBar(
                                checkItemSelected = { destination ->
                                    // Extraemos el route actual de currentDestination
                                    val routeString = currentDestination?.route?.substringAfter("route=")
                                    // Obtenemos el nombre calificado del destino que se pasa a checkItemSelected
                                    val destinationString = destination::class.qualifiedName ?: ""
                                    // Comparamos el route actual con el destino
                                    println("Route String: $routeString, Destination String: $destinationString")
                                    routeString == destinationString
                                },
                                onNavItemClick = { destination ->
                                    navController.navigate(destination) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = LoginDestination::class,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        //-----------------------------------------------------------------------------
                        // Pantalla de Login
                        loginScreen(
                            onLoginClick = {
                                // Al navegar a Characters, eliminamos el Login del backstack
                                navController.navigate(CharacterDestination) {
                                    popUpTo(LoginDestination) {
                                        inclusive = true // Limpia la pantalla de login del backstack
                                    }
                                }
                            }
                        )
                        //-----------------------------------------------------------------------------
                        //Nested Navigation de Personajes

                        //Falta definir para el navigation bar
                        navigation<Character>(startDestination = CharacterDestination){
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
                        //-----------------------------------------------------------------------------
                        //Nested Navigation de Ubicaciones
                        //Falta definir para el navigation bar
                        navigation<Location>(startDestination = LocationDestination){
                            //Pantalla de lista de ubicaciones
                            //Lógica para el movimiento hacia la siguiente pantalla
                            locationScreen(
                                onLocationClick = { location ->
                                    navController.navigateToLocationDetailScreen(
                                        destination = LocationDetailDestination(
                                            locationId = location
                                        )
                                    )
                                }
                            )

                            //Pantalla de detalle de ubicación
                            //Lógica de navegación de esta pantalla
                            //Solo se regresa :)
                            locationDetailScreen(
                                onNavigateBack = {
                                    navController.navigateUp()
                                }
                            )
                        }
                        //-----------------------------------------------------------------------------
                        //Pantalla de Perfil
                        //Sin un nested navigation de momento, probablemente se implemente más adelante
                        profileScreen(
                            onLogoutClick = {
                                navController.navigate(LoginDestination){
                                    popUpTo(0) //Borra el stack completo, ya no queda registro de nada
                                    //Si da back, salimos del app :)
                                }
                            }
                        )
                        //-----------------------------------------------------------------------------)
                    }
                }
            }
        }
    }
}




