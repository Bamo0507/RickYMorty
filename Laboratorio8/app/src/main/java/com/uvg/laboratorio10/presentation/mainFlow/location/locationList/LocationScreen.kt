package com.uvg.laboratorio10.presentation.mainFlow.location.locationList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uvg.laboratorio10.data.model.Location
import com.uvg.laboratorio10.presentation.mainFlow.ErrorScreen
import kotlinx.coroutines.delay

@Composable
fun LocationRoute(
    viewModel: LocationViewModel = viewModel(),
    onLocationClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // Llamar a getLocationData() automáticamente cuando se monta la pantalla
    LaunchedEffect(Unit) {
        viewModel.getListLocations()
    }

    LocationListScreen(
        state = state,
        onLocationClick = onLocationClick,
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
fun LocationListScreen(
    state: LocationState,
    onLocationClick: (Int) -> Unit,
    onRetryClick: () -> Unit, // Acción para reintentar
    onLoadingClick: () -> Unit, // Acción cuando se hace click mientras se está cargando
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Locations",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        Surface(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // Mostrar diferentes contenidos basados en el estado actual
            LocationListContent(
                locations = state.data,
                isLoading = state.isLoading,
                hasError = state.hasError,
                onRetryClick = onRetryClick,
                onLoadingClick = onLoadingClick,
                onLocationClick = onLocationClick,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun LocationListContent(
    locations: List<Location>?,
    isLoading: Boolean,
    hasError: Boolean,
    onRetryClick: () -> Unit,
    onLoadingClick: () -> Unit,
    onLocationClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        when {
            hasError -> {
                ErrorScreen(
                    modifier = Modifier.fillMaxSize(),
                    onRetryClick = onRetryClick,
                    typeError = "lista de ubicaciones"
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

            locations != null -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(locations) { location ->
                        LocationItem(location = location, onClick = onLocationClick)
                    }
                }
            }
        }
    }
}

@Composable
fun LocationItem(location: Location, onClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick(location.id) },
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = location.name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = location.type,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
