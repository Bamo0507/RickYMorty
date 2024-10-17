package com.uvg.laboratorio10.presentation.mainFlow.character.characterDetails

import com.uvg.laboratorio10.data.model.Character

//State con 3 Variables
//Info de personaje y estados de carga y error
data class CharacterDetailsState(
    val data: Character? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false
)