package com.uvg.laboratorio10.presentation.mainFlow.location.locationDetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uvg.laboratorio10.data.model.Location
import com.uvg.laboratorio10.presentation.mainFlow.ErrorScreen
import com.uvg.laboratorio10.presentation.ui.theme.Laboratorio9Theme

@Composable
fun LocationDetailsRoute(
    viewModel: LocationDetailsViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // Llamar a getLocationData() automáticamente cuando se monta la pantalla
    LaunchedEffect(Unit) {
        viewModel.getLocationData()
    }

    LocationDetailsScreen(
        state = state,
        onNavigateBack = onNavigateBack,
        onRetryClick = {
            viewModel.onRetryClick() // Llamar al reintento cuando el usuario haga clic en "Reintentar"
        },
        onLoadingClick = {
            viewModel.onLoadingClick() // Llamar a la acción cuando se haga clic en la pantalla de carga
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetailsScreen(
    state: LocationDetailsState,
    onNavigateBack: () -> Unit,
    onRetryClick: () -> Unit, // Acción para reintentar
    onLoadingClick: () -> Unit, // Acción cuando se hace click mientras se está cargando
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Location Details",
                        color = MaterialTheme.colorScheme.onPrimary)
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary)
                    }
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
            // Pasar los eventos a la función LocationDetailsContent
            LocationDetailsContent(
                location = state.data,
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
fun LocationDetailsContent(
    location: Location?,
    isLoading: Boolean,
    hasError: Boolean,
    onRetryClick: () -> Unit, // Acción para reintentar
    onLoadingClick: () -> Unit, // Acción cuando se hace click mientras se está cargando
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        when {
            //
            hasError -> {
                ErrorScreen(modifier = Modifier.fillMaxSize(), onRetryClick = onRetryClick,
                    typeError = "información de ubicación")

            }

            // Mostrar la pantalla de loading si isLoading es true
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

            // Mostrar la información de la ubicación cuando se tiene
            location != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 64.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = location.name,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LocationDetailRow(
                        label = "ID",
                        value = location.id.toString(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    LocationDetailRow(
                        label = "Type",
                        value = location.type,
                        modifier = Modifier.fillMaxWidth()
                    )
                    LocationDetailRow(
                        label = "Dimension",
                        value = location.dimension,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}




@Composable
fun LocationDetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label:",
            textAlign = TextAlign.Start)
        Text(text = value,
            textAlign = TextAlign.End)
    }
}

