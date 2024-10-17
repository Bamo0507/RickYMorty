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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uvg.laboratorio10.R


//Método route que se manda a llamar en el navgraphbuilder
@Composable
fun LoginRoute(onLoginClick: () -> Unit,
                modifier: Modifier = Modifier){
    LoginScreen(onLoginClick = onLoginClick, modifier = modifier)
}


//Método de la pantalla
@Composable
fun LoginScreen(onLoginClick: () -> Unit, modifier: Modifier = Modifier) {
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

                // Botón de entrar
                Button(
                    onClick = onLoginClick, //Pasamos la lambda para el onclick (será la navegación)
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

@Composable
@Preview(showBackground = true)
fun loginPreview(){
    LoginScreen(onLoginClick = { /*TODO*/ })
}


