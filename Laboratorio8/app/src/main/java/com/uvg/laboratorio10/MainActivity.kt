package com.uvg.laboratorio10

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.rememberNavController
import com.uvg.laboratorio10.dataPrefs.DataStoreUserPrefs
import com.uvg.laboratorio10.presentation.navigation.AppNavigation
import com.uvg.laboratorio10.presentation.ui.theme.Laboratorio9Theme

class MainActivity : ComponentActivity() {

    private val Context.dataStore by preferencesDataStore(name = "user_prefs")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userPreferences = DataStoreUserPrefs(dataStore)

        setContent {
            val navController = rememberNavController()
            Laboratorio9Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    AppNavigation(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        navController = navController,
                        userPreferences = userPreferences
                    )
                }
            }
        }
    }
}




