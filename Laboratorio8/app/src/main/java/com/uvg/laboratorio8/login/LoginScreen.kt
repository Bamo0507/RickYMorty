package com.uvg.laboratorio8.login

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.uvg.laboratorio8.ui.theme.Laboratorio8Theme
import com.uvg.laboratorio8.R

@Composable
fun LoginRoute(onLoginClick: () -> Unit,
                modifier: Modifier = Modifier){
    LoginScreen(onLoginClick = onLoginClick, modifier = modifier)
}


@Composable
fun LoginScreen(onLoginClick: () -> Unit, modifier: Modifier = Modifier) {
    // Box para el fondo gris
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

                // Botón de entrar
                Button(
                    onClick = onLoginClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(text = "Entrar")
                }

            }
        }

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



