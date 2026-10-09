package com.example.estado_claire

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun InicioScreen(modifier: Modifier, number: Int, click:() -> Unit)
{
    Column(modifier = modifier)
    {
        Text(number.toString(), fontSize = 50.sp)
        Button(onClick = click)
        { Text("Sumar") }
    }
}