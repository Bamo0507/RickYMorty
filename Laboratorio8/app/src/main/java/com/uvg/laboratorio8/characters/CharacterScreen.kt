package com.uvg.laboratorio8.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.uvg.laboratorio8.login.loginScreen
import com.uvg.laboratorio8.ui.theme.Laboratorio8Theme
import com.uvg.laboratorio8.dataCharacters.Character
import com.uvg.laboratorio8.dataCharacters.CharacterDb

@Composable
fun CharacterRoute(
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    val charactersState by remember { mutableStateOf(CharacterDb().getAllCharacters()) }

    CharacterScreen(onCharacterClick = onCharacterClick, characters = charactersState, modifier = modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreen(modifier: Modifier = Modifier,
                    characters: List<Character>,
                    onCharacterClick: (Int) -> Unit) {
    // Usamos Column para organizar la TopAppBar y el contenido de la lista
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "Characters",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge
            )
        }


        // Lista de personajes directamente debajo de la TopAppBar
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()  // La lista ocupa el resto de la pantalla
        ) {
            items(characters.size) { character ->
                CharacterItem(character = characters[character], onClick = onCharacterClick)
            }
        }
    }
}

//Función para cada uno de los perfiles
@Composable
fun CharacterItem(character: Character, onClick: (Int) -> Unit){
    //Row para estructura de los perfiles
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clickable { onClick(character.id) },
        verticalAlignment = Alignment.CenterVertically){
        //Imagen para perfiles
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(character.image)
                .crossfade(true)
                .build(),
            contentDescription = character.name,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        //Espacio entre foto e información
        Spacer(modifier = Modifier.width(16.dp))

        //Columna para información
        Column{
            Text(text = character.name, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
            Text(text = "${character.species} - ${character.status}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

//Lista de perfiles
@Composable
fun CharacterList(characters: List<Character>, onCharacterClick: (Int) -> Unit, modifier: Modifier = Modifier){
    LazyColumn {
        items(characters.size) { character ->
            CharacterItem(character = characters[character], onClick = onCharacterClick)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCharacterScreen() {
    // Crea una instancia de CharacterDb y obtén la lista de personajes
    val characterDb = CharacterDb()
    val characters = characterDb.getAllCharacters()

    Laboratorio8Theme {
        Surface(modifier = Modifier.fillMaxSize()) {
            CharacterScreen(
                characters = characters,
                onCharacterClick = {}
            )
        }
    }
}