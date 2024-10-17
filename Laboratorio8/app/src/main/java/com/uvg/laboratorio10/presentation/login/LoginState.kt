package com.uvg.laboratorio10.presentation.login

// Clase que define el estado de la pantalla de login
data class LoginState(
    val name: String = "",      // Nombre del usuario
    val isEmpty: Boolean = true // Indica si el campo está vacío
)
