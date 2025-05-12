package com.uvg.laboratorio10.presentation.mainFlow.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uvg.laboratorio10.R
import com.uvg.laboratorio10.presentation.mainFlow.character.characterDetails.CharacterDetailRow
import com.uvg.laboratorio10.presentation.mainFlow.profile.ProfileViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uvg.laboratorio10.domain.UserPreferences

@Composable
fun ProfileRoute(
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier,
    preferences: UserPreferences
){
    val viewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(preferences)
    )
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    ProfileScreen(
        state = state,
        onLogoutClick = {
            viewModel.logOut()
            onLogoutClick()
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    state: ProfileState,
    modifier: Modifier = Modifier,
    onLogoutClick: () -> Unit
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
        content = { padding ->
            Surface(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                if (state.isLoading) {

                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.fotoperfil),
                            contentDescription = "Foto de perfil",
                            modifier = Modifier
                                .size(200.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Mostrar el nombre del usuario desde el estado
                        CharacterDetailRow("Nombre", state.userName)
                        CharacterDetailRow("Carné", "23542")

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedButton(
                            onClick = onLogoutClick,
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                contentColor = MaterialTheme.colorScheme.primary
                            ),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(text = "Cerrar Sesión")
                        }
                    }
                }
            }
        }

    )
}
