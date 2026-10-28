package com.example.estado_claire.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ContadorOcupacion(
    totalDentro: Int,
    capacidad: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            "$totalDentro / $capacidad",
            Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 48.sp
        )
        Spacer(Modifier.padding(4.dp))
        Text(
            "personas en la sala",
            Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
    }
}

@Composable
fun BarraOcupacion(
    porcentaje: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(modifier.fillMaxWidth().height(8.dp).background(Color.Gray)) {
        Box(
            Modifier
                .background(color)
                .fillMaxWidth(porcentaje / 100f)
                .fillMaxHeight()
        )
    }
}

@Composable
fun TextoPorcentaje(
    porcentaje: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Spacer(Modifier.padding(4.dp))
        Text(
            "$porcentaje% de ocupación",
            Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
        Text(
            "Aforo disponible",
            Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
    }
}