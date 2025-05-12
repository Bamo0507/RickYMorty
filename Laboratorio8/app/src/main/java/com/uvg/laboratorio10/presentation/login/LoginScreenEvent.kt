package com.uvg.laboratorio10.presentation.login

// Interfaz que define los eventos de la pantalla de login
interface LoginScreenEvent {
    data class UserNameChange(val name: String) : LoginScreenEvent
    data object LoginClick : LoginScreenEvent
}
