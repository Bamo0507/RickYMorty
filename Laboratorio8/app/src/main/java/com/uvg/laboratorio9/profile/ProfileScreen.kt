package com.uvg.laboratorio9.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uvg.laboratorio9.R
import com.uvg.laboratorio9.characterDetails.CharacterDetailRow
import com.uvg.laboratorio9.components.BottomNavigationBar
import com.uvg.laboratorio9.ui.theme.Laboratorio9Theme

@Composable
fun ProfileRoute(
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController
){
    ProfileScreen(onLogoutClick = onLogoutClick, modifier = modifier, navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier, onLogoutClick: () -> Unit,
    navController: NavController
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Profile",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            //Aquí se estará implementando la navegación
            BottomNavigationBar(navController = navController)

        },
        content = { padding ->
            Surface(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                //Imagen con información del perfil
                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center){
                    //Imagen del perfil
                    Image(
                        painter = painterResource(id = R.drawable.fotoperfil),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    //Espaciado antes del texto de informaciòn
                    Spacer(modifier = Modifier.height(16.dp))

                    //Textos de informaciòn de la cuenta
                    CharacterDetailRow("Nombre", "Bryan Alberto Martìnez Orellana")
                    CharacterDetailRow("Carnè", "23542")

                    //Espaciado antes del botòn
                    OutlinedButton(onClick = onLogoutClick,
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Text(text = "Cerrar Sesión")
                    }
                }

            }

        }

    )
}
