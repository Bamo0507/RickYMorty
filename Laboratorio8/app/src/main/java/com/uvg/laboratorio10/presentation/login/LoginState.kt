package com.uvg.laboratorio10.presentation.login

// Clase que define el estado de la pantalla de login
data class LoginState(
    val name: String = "",
    val isEmpty: Boolean = true,
    val isSynchronizing: Boolean = false // Agregar esta línea
)
