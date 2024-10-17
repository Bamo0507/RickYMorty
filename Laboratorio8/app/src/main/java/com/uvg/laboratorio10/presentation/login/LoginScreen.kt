package com.uvg.laboratorio10.presentation.login

import android.view.Surface
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uvg.laboratorio10.R
import com.uvg.laboratorio10.presentation.login.LoginScreenEvent
import com.uvg.laboratorio10.presentation.login.LoginViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uvg.laboratorio10.domain.UserPreferences


//Método route que se manda a llamar en el navgraphbuilder
@Composable
fun LoginRoute(
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
    preferences: UserPreferences
){
    val owner = LocalSavedStateRegistryOwner.current
    val viewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(preferences, owner)
    )
    LoginScreen(onLoginClick = onLoginClick, modifier = modifier, viewModel = viewModel)
}



//Método de la pantalla
@Composable
fun LoginScreen(onLoginClick: () -> Unit, modifier: Modifier = Modifier, viewModel: LoginViewModel) {
    //Variable para poder leer los estados de la pantalla
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    //Variable para manejar el estado para el login
    val loginSuccess = viewModel.loginSuccess.collectAsStateWithLifecycle()

    LaunchedEffect(loginSuccess.value) {
        if (loginSuccess.value) {
            onLoginClick()
        }
    }


    // Box para el fondo gris de Rick y Mory
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center){

                }
                // Logo de Rick and Morty
                Image(
                    painter = painterResource(id = R.drawable.logorickmorty),
                    contentDescription = "Logo",
                    modifier = Modifier,
                    contentScale = ContentScale.Fit
                )

                OutlinedTextField(
                    value = state.value.name,
                    onValueChange = { viewModel.onEvent(LoginScreenEvent.UserNameChange(it)) },
                    label = { Text(text = "Nombre de usuario") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                )

                // Botón de entrar
                Button(
                    onClick = {viewModel.onEvent(LoginScreenEvent.LoginClick)},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    enabled = !state.value.isEmpty
                ) {
                    Text(text = "Iniciar Sesión")
                }

            }
        }


        //BOX - inferior para mi identificación
        Box(modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter){
            // Texto para la parte inferior
            Text(
                text = "Bryan Alberto Martínez Orellana - #23542",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )
        }
    }
}



