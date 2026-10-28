package com.example.estado_claire.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.estado_claire.logic.CAPACIDAD_MAXIMA
import com.example.estado_claire.logic.SalaEstado

@Composable
fun TarjetaOcupacion(
    sala: SalaEstado,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .background(Color(0xAA33CC33))
            .fillMaxWidth()
            .aspectRatio(1.25f)
            .padding(32.dp)
    ) {
        Column {
            ContadorOcupacion(sala.totalDentro, CAPACIDAD_MAXIMA)
            Spacer(Modifier.padding(4.dp))
            BarraOcupacion(sala.porcentajeDentro, sala.estado.color)
            TextoPorcentaje(sala.porcentajeDentro)
        }
    }
}

@Composable
fun BotonesControl(
    onSale: () -> Unit,
    onEntra: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier.fillMaxWidth().height(48.dp)) {
        Button(
            modifier = Modifier.fillMaxHeight().weight(1f),
            onClick = onSale
        ) { Text("Sale") }

        Spacer(Modifier.padding(8.dp))

        Button(
            modifier = Modifier.fillMaxHeight().weight(1f),
            onClick = onEntra
        ) { Text("Entra") }
    }
}

@Composable
fun SeccionVisitas(
    totalVisitas: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier.fillMaxWidth().height(192.dp).background(Color.Gray)) {
        Column(Modifier.fillMaxHeight().weight(0.75f).padding(16.dp)) {
            Text("Visitas del día")
            Text("Solo suma las entradas")
        }
        Text(
            totalVisitas.toString(),
            Modifier.weight(0.25f).fillMaxHeight(),
            fontSize = 48.sp,
            textAlign = TextAlign.Center
        )
    }
}