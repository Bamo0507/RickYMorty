package com.uvg.laboratorio10.presentation.mainFlow.character.characterDetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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

//Método Route que se manda a llamar con el método del navgraphbuilder
@Composable
fun CharacterDetailRoute(
    characterId: Int,
    viewModel: CharacterDetailsViewModel = viewModel(), //Se inicializa el viewmodel - siempre procurar hacerlo así
    onNavigateBack: () -> Unit
) {

    //Recojemos en una variable STATE lo que se registra en el uiState (maneja el flujo de la aplicación y lo que pasa en tiempo real)
    //Se logra con ayuda del método collectAsStateWithLifecycle()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // Llamar a getCharacterData() automáticamente cuando se monta la pantalla
    //Se lanza el efecto de corrutina para obtener los datos del personaje
    //el viewmodel que se inició llama al método de getCharacterData()
    LaunchedEffect(characterId) {
        viewModel.getCharacterData()
    }


    //Pasar los parámetros a la pantalla
    CharacterDetailScreen(
        state = state,
        onBackClick = onNavigateBack, //Parámetro para la navegación hacia atrás
        onRetryClick = {
            viewModel.onRetryClick() // Reintentar cargar los datos - manejado por viewmodel
        },
        onLoadingClick = {
            viewModel.onLoadingClick() // Detectar clic durante la carga - manejado por viewmodel
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
                    IconButton(onClick = onBackClick) { //Navegación hacia atrás
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
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
            //Llenamos toda la info con el state que ya recibió toda la info que necesitamos
            CharacterDetailContent(
                character = state.data, //pasamos la data que ya fue recogida por el state
                isLoading = state.isLoading, //pasamos el estado de loading
                hasError = state.hasError, //pasamos el estado de error
                onRetryClick = onRetryClick, //pasamos el método para reintentar ya definido en el viewmodel
                onLoadingClick = onLoadingClick, //pasamos el método con la pantalla de carga ya definido en el viewmodel
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun CharacterDetailContent(
    character: Character?, //Se recibe un personaje siguiendo la model data class
    isLoading: Boolean,
    hasError: Boolean,
    onRetryClick: () -> Unit,
    onLoadingClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        when {
            //Si se tiene un error detectado se muestra la pantalla de error
            hasError -> {
                ErrorScreen(
                    modifier = Modifier.fillMaxSize(),
                    onRetryClick = onRetryClick,
                    typeError = "información del personaje"
                )
            }

            //Si tenemos un est ado de loading mostramos un circular progress indicator
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

            //Si en el parámetro de Character se paso algo entonces mostramos la información
            character != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Imagen del personaje - ayuda de coil
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
