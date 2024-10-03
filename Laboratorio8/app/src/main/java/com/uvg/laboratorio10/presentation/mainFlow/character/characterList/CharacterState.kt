package com.uvg.laboratorio10.presentation.mainFlow.character.characterList

import com.uvg.laboratorio10.data.model.Character

data class CharacterState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val data: List<Character> = emptyList()
)