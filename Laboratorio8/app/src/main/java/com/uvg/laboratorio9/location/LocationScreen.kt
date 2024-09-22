package com.uvg.laboratorio9.location

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uvg.laboratorio9.components.BottomNavigationBar
import com.uvg.laboratorio9.dataLocations.Location
import com.uvg.laboratorio9.dataLocations.LocationDb
import com.uvg.laboratorio9.ui.theme.Laboratorio9Theme


@Composable
fun LocationRoute(
    onLocationClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController
){
    val locationsState by remember { mutableStateOf(LocationDb().getAllLocations()) }
    LocationScreen(onLocationClick = onLocationClick, locations = locationsState, modifier = modifier,
        navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
    modifier: Modifier = Modifier,
    locations: List<Location>,
    onLocationClick: (Int) -> Unit,
    navController: NavController
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
        },
        bottomBar = {
            // Aquí implementaré el navigation bar
            BottomNavigationBar(navController = navController)
        },
        content = { paddingValues ->
            // Contenido principal de la pantalla (la lista de ubicaciones)
            LazyColumn(
                contentPadding = paddingValues, // Para evitar superposición
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(locations) { location ->
                    LocationItem(location = location, onClick = onLocationClick)
                }
            }
        }
    )
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
