package com.example.estado_claire.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.estado_claire.model.CAPACIDAD_MAXIMA
import com.example.estado_claire.view.components.Header
import com.example.estado_claire.viewmodel.SalaViewModel

@Composable
fun Dashboard(
    modifier: Modifier = Modifier,
    viewModel: SalaViewModel = viewModel()
)
{
    val sala by viewModel.sala.collectAsStateWithLifecycle()

    Column(modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Header(
            modifier = Modifier.fillMaxWidth(),
            { Text("Biblioteca Central", fontSize = 20.sp) },
            { Text("Sala de estudio · Capacidad $CAPACIDAD_MAXIMA") }
        )

        Box(
            Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0x9A7FD98B))
                .fillMaxWidth()
                .padding(16.dp)
        )
        {
            Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(8.dp))
            {
                Text(
                    "${sala.totalDentro} / $CAPACIDAD_MAXIMA",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 48.sp
                )

                Text(
                    "Personas en la sala",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp
                )

                LinearProgressIndicator(
                    progress = { sala.porcentajeDentro / 100f },
                    modifier = Modifier.fillMaxWidth(),
                    color = sala.estadoSala.color
                )

                Text(
                    "${sala.porcentajeDentro}% de ocupación",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )

                Text(
                    "Aforo disponible",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp
                )
            }
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            OutlinedButton(
                onClick = { viewModel.decrementar() },
                Modifier.weight(1f),
                enabled = sala.puedeDecrementar
            ) { Text("- Sale", Modifier.padding(12.dp)) }

            Button(
                onClick = { viewModel.incrementar() },
                Modifier.weight(1f),
                enabled = sala.puedeIncrementar
            )
            { Text("+ Entra", Modifier.padding(12.dp)) }
        }

        Row(
            Modifier
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceDim)
                .padding(16.dp)
        )
        {
            Column(Modifier.weight(1f))
            {
                Text("Visitas del día")
                Text("Solo suma las entradas")
            }

            Text(sala.totalVisitas.toString(), fontSize = 24.sp)
        }

        Spacer(Modifier.weight(1f))

        OutlinedButton(
            onClick = { viewModel.restablecer() },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Cerrar día") }
    }
}

@Preview
@Composable
fun PreviewDashboard()
{
    Surface()
    { Dashboard() }
}
