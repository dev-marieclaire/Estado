package com.example.estado_claire.logic

import androidx.compose.runtime.saveable.listSaver
import androidx.compose.ui.graphics.Color

const val CAPACIDAD_MAXIMA = 50

enum class Estado(val color: Color, val tope: Int) {
    VACIO(Color.Green, 70),
    POBLADO(Color.Yellow, 99),
    LLENO(Color.Red, 100)
}

data class SalaEstado(
    val totalDentro: Int = 0,
    val totalVisitas: Int = 0
) {
    val porcentajeDentro: Int
        get() = totalDentro * 100 / CAPACIDAD_MAXIMA

    val estado: Estado
        get() = when {
            porcentajeDentro <= Estado.VACIO.tope   -> Estado.VACIO
            porcentajeDentro <= Estado.POBLADO.tope -> Estado.POBLADO
            else                                    -> Estado.LLENO
        }

    fun agregarPersona(): SalaEstado =
        if (totalDentro < CAPACIDAD_MAXIMA)
            copy(totalDentro = totalDentro + 1, totalVisitas = totalVisitas + 1)
        else this

    fun quitarPersona(): SalaEstado =
        if (totalDentro > 0) copy(totalDentro = totalDentro - 1)
        else this
}

// Saver para sobrevivir a rotaciones/process death con rememberSaveable
val SalaEstadoSaver = listSaver<SalaEstado, Int>(
    save    = { listOf(it.totalDentro, it.totalVisitas) },
    restore = { SalaEstado(it[0], it[1]) }
)
