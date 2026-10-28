package com.example.estado_claire.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.estado_claire.components.BotonesControl
import com.example.estado_claire.components.Header
import com.example.estado_claire.components.SeccionVisitas
import com.example.estado_claire.components.TarjetaOcupacion
import com.example.estado_claire.logic.CAPACIDAD_MAXIMA
import com.example.estado_claire.logic.SalaEstado
import com.example.estado_claire.logic.SalaEstadoSaver

@Composable
fun InicioScreen(modifier: Modifier = Modifier) {
    var sala by rememberSaveable(stateSaver = SalaEstadoSaver) {
        mutableStateOf(SalaEstado())
    }

    Column(modifier.fillMaxSize().padding(16.dp)) {
        Header(Modifier.fillMaxWidth(), "Biblioteca Central", "Sala de estudio · Capacidad $CAPACIDAD_MAXIMA")

        TarjetaOcupacion(sala)

        Spacer(Modifier.padding(8.dp))

        BotonesControl(
            onSale  = { sala = sala.quitarPersona() },
            onEntra = { sala = sala.agregarPersona() }
        )

        Spacer(Modifier.padding(8.dp))

        SeccionVisitas(sala.totalVisitas)
    }
}

@Preview
@Composable
fun PreviewInicioScreen() {
    Surface { InicioScreen(Modifier.fillMaxSize()) }
}