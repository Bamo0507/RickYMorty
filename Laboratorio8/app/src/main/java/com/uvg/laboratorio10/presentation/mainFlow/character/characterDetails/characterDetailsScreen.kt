package com.uvg.laboratorio10.presentation.mainFlow.character.characterDetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.uvg.laboratorio10.data.model.Character
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uvg.laboratorio10.data.source.CharacterDb
import com.uvg.laboratorio10.presentation.mainFlow.ErrorScreen

@Composable
fun CharacterDetailRoute(
    characterId: Int,
    viewModel: CharacterDetailsViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // Llamar a getCharacterData() automáticamente cuando se monta la pantalla
    LaunchedEffect(characterId) {
        viewModel.getCharacterData()
    }

    CharacterDetailScreen(
        state = state,
        onBackClick = onNavigateBack,
        onRetryClick = {
            viewModel.onRetryClick() // Reintentar cargar los datos
        },
        onLoadingClick = {
            viewModel.onLoadingClick() // Detectar clic durante la carga
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    state: CharacterDetailsState,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit,
    onLoadingClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Character Detail") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // Mostrar contenido según el estado actual
            CharacterDetailContent(
                character = state.data,
                isLoading = state.isLoading,
                hasError = state.hasError,
                onRetryClick = onRetryClick,
                onLoadingClick = onLoadingClick,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun CharacterDetailContent(
    character: Character?,
    isLoading: Boolean,
    hasError: Boolean,
    onRetryClick: () -> Unit,
    onLoadingClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        when {
            hasError -> {
                ErrorScreen(
                    modifier = Modifier.fillMaxSize(),
                    onRetryClick = onRetryClick,
                    typeError = "información del personaje"
                )
            }

            isLoading -> {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable { onLoadingClick() },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Cargando")
                }
            }

            character != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Imagen del personaje
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(character.image)
                            .crossfade(true)
                            .build(),
                        contentDescription = character.name,
                        modifier = Modifier
                            .size(192.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Nombre del personaje
                    Text(
                        text = character.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Detalles del personaje
                    CharacterDetailRow(label = "Species", value = character.species)
                    CharacterDetailRow(label = "Status", value = character.status)
                    CharacterDetailRow(label = "Gender", value = character.gender)
                }
            }
        }
    }
}

@Composable
fun CharacterDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 48.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Start
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.End
        )
    }
}
