package com.example.estado_claire

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.estado_claire.components.MiCard

@Composable
fun InicioScreen(modifier: Modifier = Modifier) {
    val nombreList = remember { mutableStateListOf<String>(
        "Ramiro", "Pablo", "Renata", "Nelly", "Sebastián", "Kenneth",
        "Ramiro", "Pablo", "Renata", "Nelly", "Sebastián", "Kenneth",
        "Ramiro", "Pablo", "Renata", "Nelly", "Sebastián", "Kenneth"
    ) }

    Column(modifier = modifier.fillMaxSize().padding(8.dp))
    {
        LazyColumn(modifier = Modifier.fillMaxSize())
        {
            items(nombreList)
            { item ->
                MiCard(
                    modifier = Modifier.fillMaxWidth().height(64.dp),
                    Color(0xFFFCD8D8),
                    item
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview
@Composable
fun PreviewInicioScreen()
{
    Surface()
    {
        InicioScreen(Modifier.fillMaxSize())
    }
}
