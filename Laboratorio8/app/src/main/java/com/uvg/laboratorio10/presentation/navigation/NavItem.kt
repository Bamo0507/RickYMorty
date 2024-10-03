package com.uvg.laboratorio10.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.uvg.laboratorio10.presentation.mainFlow.character.CharacterNavGraph
import com.uvg.laboratorio10.presentation.mainFlow.character.characterList.CharacterDestination
import com.uvg.laboratorio10.presentation.mainFlow.location.LocationsNavGraph
import com.uvg.laboratorio10.presentation.mainFlow.location.locationList.LocationDestination
import com.uvg.laboratorio10.presentation.mainFlow.profile.ProfileDestination

data class NavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val destination: Any, // Utilizamos Any ya que debemos poner aquí nuestros Destinations
)

val navigationItems = listOf(
    NavItem(
        title = "Characters",
        selectedIcon = Icons.Filled.Groups,
        unselectedIcon = Icons.Outlined.Groups,
        destination = CharacterNavGraph
    ),
    NavItem(
        title = "Locations",
        selectedIcon = Icons.Filled.LocationOn,
        unselectedIcon = Icons.Outlined.LocationOn,
        destination = LocationsNavGraph
    ),
    NavItem(
        title = "Profile",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        destination = ProfileDestination
    )
)

val topLevelDestinations = listOf(
    CharacterDestination::class,
    LocationDestination::class,
    ProfileDestination::class
)
