package com.example.estado_claire

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun InicioScreen(modifier: Modifier = Modifier) {
    val name_list = listOf<String>("Ramiro", "Carlos", "Sofia", "Fernanda", "Pablo", "Victor")

    Column(modifier = modifier)
    { name_list.forEach { nombre -> Text(nombre) } }
}
