package com.uvg.laboratorio10.presentation.mainFlow.profile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.uvg.laboratorio10.domain.UserPreferences
import kotlinx.serialization.Serializable

@Serializable
data object ProfileDestination

fun NavGraphBuilder.profileScreen(
    onLogoutClick: () -> Unit,
    preferences: UserPreferences
){
    composable<ProfileDestination> {
        ProfileRoute(
            onLogoutClick = onLogoutClick,
            modifier = Modifier.fillMaxWidth(),
            preferences = preferences
        )
    }
}
