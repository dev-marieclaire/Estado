package com.example.estado_claire

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


enum class Estado
{
    VACIO, POBLADO, LLENO
}

val couleurs = listOf(Color.Green, Color.Yellow, Color.Red)

@Composable
fun InicioScreen(modifier: Modifier = Modifier) {
    var total_dentro by rememberSaveable { mutableStateOf(0) }
    var total_visitas by rememberSaveable { mutableStateOf(0) }

    var porcentaje_dentro = (total_dentro * 100 / 50).toInt()

    var text_total_dentro = total_dentro.toString() + " / 50"
    var text_total_visitas = total_visitas.toString()
    var text_porcentaje_dentro = (porcentaje_dentro).toString() + "% de ocupación"

    var current_state by rememberSaveable { mutableStateOf(Estado.VACIO) }

    Column(Modifier.fillMaxSize().padding(16.dp))
    {
        Text("Biblioteca Central", fontSize = 32.sp)
        Spacer(Modifier.padding(8.dp))
        Text("Sala de estudio · Capacidad: 50", fontSize = 16.sp)
        Spacer(Modifier.padding(8.dp))

        Box(Modifier.background(Color(0xAA33CC33)).fillMaxWidth().aspectRatio(1.25f).padding(32.dp))
        {
            Column()
            {
                Text(
                    text_total_dentro,
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
                Spacer(Modifier.padding(4.dp))
                Box(Modifier.background(Color.Gray).fillMaxWidth().height(8.dp))
                {
                    val color = if (current_state == Estado.VACIO) 0
                                else if (current_state == Estado.POBLADO) 1
                                else 2
                    Box(
                        Modifier.background(couleurs[color])
                            .fillMaxWidth(porcentaje_dentro / 100f) // Aquí va a ir el porcentaje.
                            .fillMaxHeight())
                }
                Spacer(Modifier.padding(4.dp))
                Text(
                    text_porcentaje_dentro,
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

        Spacer(Modifier.padding(8.dp))

        // BOTONES
        Row(Modifier.fillMaxWidth().height(48.dp))
        {
            Button(
                modifier = Modifier.fillMaxHeight().weight(1f),
                onClick = {
                    if (total_dentro > 0) total_dentro -= 1

                    current_state = if (porcentaje_dentro < 40) Estado.VACIO
                                    else if (porcentaje_dentro >= 40 && porcentaje_dentro < 100) Estado.POBLADO
                                    else Estado.LLENO
                }
            )
            { Text("Sale") }

            Spacer(Modifier.padding(8.dp))

            Button(
                modifier = Modifier.fillMaxHeight().weight(1f),
                onClick = {
                    if (total_dentro > 0) total_dentro += 1

                    current_state = if (porcentaje_dentro < 40) Estado.VACIO
                                    else if (porcentaje_dentro >= 40 && porcentaje_dentro < 100) Estado.POBLADO
                                    else Estado.LLENO
                }
            )
            { Text("Entra") }
        }

        Spacer(Modifier.padding(8.dp))

        Row(Modifier.fillMaxWidth().height(192.dp).background(Color.Gray))
        {
            Column(Modifier.fillMaxHeight().weight(3/4f).padding(16.dp))
            {
                Text("Visitas del día")
                Text("Solo suma las entradas")
            }
            Text(text_total_visitas, Modifier.weight(1/4f).fillMaxHeight(), fontSize = 48.sp, textAlign = TextAlign.Center)
        }
    }
}

@Preview
@Composable
fun PreviewInicioScreen()
{
    Surface {
        InicioScreen(Modifier.fillMaxSize())
    }
}
