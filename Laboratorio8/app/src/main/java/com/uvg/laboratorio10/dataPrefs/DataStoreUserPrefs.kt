package com.uvg.laboratorio10.dataPrefs

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.uvg.laboratorio10.domain.UserPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class DataStoreUserPrefs(
    private val dataStore: DataStore<Preferences>
): UserPreferences {

    //RECORDAR EL SUSPEND LO HACEMOS SIEMPRE QUE NOS VAMOS A METER CON LOS KEYS SIN USAR FLOW (SET O GET)

    //Definición de claves para guardar datos en el DataStore
    private val nameKey = stringPreferencesKey("name")
    private val loggedKey = booleanPreferencesKey("logged")

    //Método para marcar al usuario como loggeado
    override suspend fun logIn(){
        dataStore.edit { preferences ->
            preferences[loggedKey] = true
        }
    }

    //Método para marcar al usuario como deslogeado
    override suspend fun logOut(){
        dataStore.edit { preferences ->
            preferences[loggedKey] = false
            preferences.remove(nameKey)
        }
    }

    //Método para saber cuál es el estado del usuario (logged o no)
    override fun loggedStatus(): Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[loggedKey] ?: false
        }

    //Método para guardar el nombre del usuario
    override suspend fun setUserName(name: String) {
        dataStore.edit { preferences ->
            preferences[nameKey] = name
        }
    }

    //Método para recuperar el nombre de usuario
    override suspend fun getUserName(): String? {
        val preferences = dataStore.data.first() //obtener el primer valor emitido por el flujo
        return preferences[nameKey] //recuperar el valor asociado a la clave nameKey
    }


}