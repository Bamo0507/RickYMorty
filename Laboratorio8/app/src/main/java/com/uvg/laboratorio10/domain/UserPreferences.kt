package com.uvg.laboratorio10.domain

import kotlinx.coroutines.flow.Flow

//Interfaz en donde defino todos los métodos que voy a manejar con DataStore
interface UserPreferences {
    suspend fun logIn()
    suspend fun logOut()
    fun loggedStatus(): Flow<Boolean>
    suspend fun setUserName(name: String)
    suspend fun getUserName(): String?
}