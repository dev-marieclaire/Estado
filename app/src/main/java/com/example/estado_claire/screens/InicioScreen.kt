package com.example.estado_claire

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.estado_claire.components.MiCard


@Composable
fun InicioScreen(modifier: Modifier = Modifier) {
    val name_list = listOf<String>("Ramiro", "Carlos", "Sofia", "Fernanda", "Pablo", "Victor", "Ramiro", "Carlos", "Sofia", "Fernanda", "Pablo", "Victor")

    LazyColumn(modifier = modifier.padding(16.dp))
    {
        items(name_list)
        { nombre ->
            MiCard(modifier = Modifier.fillMaxWidth().height(64.dp), Color.Cyan, nombre)
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}
