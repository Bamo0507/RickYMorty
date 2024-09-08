package com.uvg.laboratorio8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.uvg.laboratorio8.characterDetails.CharacterDetailDestination
import com.uvg.laboratorio8.characterDetails.characterDetailScreen
import com.uvg.laboratorio8.characterDetails.navigateToCharacterDetailScreen
import com.uvg.laboratorio8.ui.theme.Laboratorio8Theme
import com.uvg.laboratorio8.login.LoginDestination
import com.uvg.laboratorio8.login.loginScreen
import com.uvg.laboratorio8.characters.characterScreen
import com.uvg.laboratorio8.characters.CharacterDestination

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Laboratorio8Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = LoginDestination,
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    ) {
                        loginScreen(
                            onLoginClick = {
                                navController.navigate(CharacterDestination){
                                    popUpTo(LoginDestination) {
                                        inclusive = true
                                    }
                                }
                            }
                        )

                        characterScreen(
                            onCharacterClick = { character ->
                                navController.navigateToCharacterDetailScreen(
                                    destination = CharacterDetailDestination(
                                        characterId = character
                                    )
                                )
                            }
                        )

                        characterDetailScreen(
                            onNavigateBack = {
                                navController.navigateUp()
                            }
                        )
                    }
                }


            }
        }
    }
}