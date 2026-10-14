package com.example.estado_claire.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MiCard(modifier: Modifier = Modifier, nombre: String, color: Color, update: () -> Unit, delete: () -> Unit)
{
    Row(modifier = modifier.fillMaxSize().background(color).padding(8.dp))
    {
        Text(nombre, modifier = modifier.weight(1f), fontSize = 32.sp, textAlign = TextAlign.Center)

        Button(onClick = update, modifier = Modifier.padding())
        { Text("U", fontSize = 16.sp) }

        Spacer(Modifier.padding(4.dp))

        Button(onClick = delete, modifier = Modifier.padding())
        { Text("D", fontSize = 16.sp) }
    }
}

@Preview
@Composable
fun PreviewMiCard()
{
    MiCard(modifier = Modifier.fillMaxWidth().height(64.dp), "nepomuseno", Color.Cyan,{}, {})
}