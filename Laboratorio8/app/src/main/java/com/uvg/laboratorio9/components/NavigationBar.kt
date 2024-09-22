package com.uvg.laboratorio9.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.uvg.laboratorio9.characters.CharacterDestination
import com.uvg.laboratorio9.location.LocationDestination
import com.uvg.laboratorio9.login.LoginDestination
import com.uvg.laboratorio9.profile.ProfileDestination
import com.uvg.laboratorio9.ui.theme.Laboratorio9Theme
import kotlinx.serialization.descriptors.StructureKind

@Composable
fun BottomNavigationBar(
    navController: NavController // Recibir el NavController
) {
    val items = listOf(
        NavigationItem.Characters,
        NavigationItem.Locations,
        NavigationItem.Profile
    )

    // Maintain the selected index with a state mutable of type Int
    var selectedIndex by rememberSaveable { mutableStateOf(0) }  // Initialize to 0


    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination.toString()

        items.forEachIndexed { index, item ->

            val isSelected = (currentDestination == item.currentDestination)

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    // Update the selected index and navigate
                    selectedIndex = index
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                },
                icon = {
                    // Icon logic based on isSelected
                    if (isSelected) {
                        Card(
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.inversePrimary
                            ),
                            modifier = Modifier.size(width = 80.dp, height = 40.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title, // Better accessibility
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    } else {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title, // Better accessibility
                            tint = MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primaryContainer
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSurface,
                    unselectedIconColor = MaterialTheme.colorScheme.primaryContainer,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

// Sealed classes para los ítems de navegación
sealed class NavigationItem(val icon: ImageVector, val title: String, val route: Any, val currentDestination: String) {
    object Characters : NavigationItem(Icons.Filled.People, "Characters", CharacterDestination,"Destination(0xafa0acbd) route=com.uvg.laboratorio9.characters.CharacterDestination")
    object Locations : NavigationItem(Icons.Filled.Public, "Locations", LocationDestination, "Destination(0x8dee5b4c) route=com.uvg.laboratorio9.location.LocationDestination")
    object Profile : NavigationItem(Icons.Filled.Person, "Profile", ProfileDestination,"Destination(0xd4812c36) route=com.uvg.laboratorio9.profile.ProfileDestination")
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    val navController = rememberNavController()
    Laboratorio9Theme {
        var selectedItem by rememberSaveable { mutableStateOf(0) }
        BottomNavigationBar(
            navController = navController
        )
    }
}
