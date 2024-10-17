package com.uvg.laboratorio10.presentation.mainFlow.character.characterList

import com.uvg.laboratorio10.data.model.Character

//Data class para el estado
//Se recibe la lista de los personajes y los estados de error y carga
data class CharacterState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val data: List<Character> = emptyList()
)