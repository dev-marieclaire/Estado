package com.example.estado_claire

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.estado_claire.components.CalcButton

@Composable
fun AforoScreen(modifier: Modifier)
{
    var personas_actuales by rememberSaveable { mutableStateOf(0) }
    var visitas by rememberSaveable { mutableStateOf(0) }
    val porcentaje = personas_actuales / 50.0

    Column(modifier = modifier.fillMaxSize().padding(16.dp))
    {
        Column(modifier = modifier.fillMaxWidth().background(Color.Green))
        {
            Text(personas_actuales.toString() + " / 50")
            Text("personas en la sala")
            Text(porcentaje.toString() + "% de ocupacion.")
            Text("Aforo disponible")
        }

        Row(modifier = Modifier.fillMaxWidth().height(24.dp))
        {
            Button(modifier = Modifier.weight(1f), onClick = {
                personas_actuales -= 1
                visitas -= 1
            })
            { Text("Sale") }
            Button(modifier = Modifier.weight(1f), onClick = {
                personas_actuales += 1
                visitas += 1
            })
            { Text("Entra") }
        }
    }
}

@Preview
@Composable
fun PreviewAforoScreen()
{
    Surface()
    {
        AforoScreen(Modifier.fillMaxSize())
    }
}