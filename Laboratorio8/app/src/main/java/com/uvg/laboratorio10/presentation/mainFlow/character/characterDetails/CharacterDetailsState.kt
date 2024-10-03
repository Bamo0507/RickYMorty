package com.uvg.laboratorio10.presentation.mainFlow.character.characterDetails

import com.uvg.laboratorio10.data.model.Character

data class CharacterDetailsState(
    val data: Character? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false
)