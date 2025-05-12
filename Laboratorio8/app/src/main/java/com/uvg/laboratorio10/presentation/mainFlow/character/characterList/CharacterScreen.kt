package com.uvg.laboratorio10.presentation.mainFlow.character.characterList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.request.ImageRequest
import com.uvg.laboratorio10.data.model.Character
import com.uvg.laboratorio10.data.source.CharacterDb
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uvg.laboratorio10.presentation.mainFlow.ErrorScreen


@Composable
fun CharacterRoute(
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val owner = LocalSavedStateRegistryOwner.current
    val viewModel: CharacterViewModel = viewModel(
        factory = CharacterViewModelFactory(context, owner)
    )

    //En tiempo real se actualiza el estado de la variable de state acorde al ciclo de vida
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // Llamar a getListCharacters() automáticamente cuando se monta la pantalla
    LaunchedEffect(Unit) {
        viewModel.getListCharacters()
    }

    //Se pasan todos los parámetros necesarios a la pantalla
    CharacterScreen(
        state = state,
        onCharacterClick = onCharacterClick,
        onRetryClick = {
            viewModel.onRetryClick() // Reintentar cargar los datos
        },
        onLoadingClick = {
            viewModel.onLoadingClick() // Detectar clic durante la carga
        },
        modifier = modifier
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreen(
    state: CharacterState,
    onCharacterClick: (Int) -> Unit,
    onRetryClick: () -> Unit, // Acción para reintentar
    onLoadingClick: () -> Unit, // Acción cuando se hace click mientras se está cargando
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Characters",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                CharacterContent(
                    characters = state.data,
                    isLoading = state.isLoading,
                    hasError = state.hasError,
                    onRetryClick = onRetryClick,
                    onLoadingClick = onLoadingClick,
                    onCharacterClick = onCharacterClick,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    )
}
@Composable
fun CharacterContent(
    characters: List<Character>?,
    isLoading: Boolean,
    hasError: Boolean,
    onRetryClick: () -> Unit,
    onLoadingClick: () -> Unit,
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        when {
            //Se manda a llamar a la error screen
            hasError -> {
                ErrorScreen(
                    modifier = Modifier.fillMaxSize(),
                    onRetryClick = onRetryClick,
                    typeError = "lista de personajes"
                )
            }

            isLoading -> {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable { onLoadingClick() }, // Detectar el clic durante el loading
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Cargando")
                }
            }

            //Si la data que se paso no es nula, entonces se genera el lazycolumn con los datos
            characters != null -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(characters) { character ->
                        CharacterItem(character = character, onClick = onCharacterClick)
                    }
                }
            }
        }
    }
}



//Función para cada uno de los perfiles
@Composable
fun CharacterItem(character: Character, onClick: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick(character.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen del personaje
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(character.image)
                .crossfade(true)
                .build(),
            contentDescription = character.name,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Información del personaje
        Column {
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "${character.species} - ${character.status}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
