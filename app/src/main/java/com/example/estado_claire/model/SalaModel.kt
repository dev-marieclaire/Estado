package com.example.estado_claire.model

import androidx.compose.ui.graphics.Color

const val CAPACIDAD_MAXIMA = 50

enum class EstadoSala(val color: Color, val tope: Int)
{
    VACIO(Color.Green, 70),
    POBLADO(Color.Yellow, 99),
    LLENO(Color.Red, 100)
}

data class SalaModel(
    val totalDentro: Int = 0,
    val totalVisitas: Int = 0
) {
    val porcentajeDentro: Int
        get() = totalDentro * 100 / CAPACIDAD_MAXIMA

    val puedeIncrementar: Boolean  get() = totalDentro < CAPACIDAD_MAXIMA
    val puedeDecrementar: Boolean get() = totalDentro > 0

    val estadoSala: EstadoSala
        get() = when {
            porcentajeDentro <= EstadoSala.VACIO.tope   -> EstadoSala.VACIO
            porcentajeDentro <= EstadoSala.POBLADO.tope -> EstadoSala.POBLADO
            else                                    -> EstadoSala.LLENO
        }

    fun incrementar(): SalaModel =
        if (puedeIncrementar)
        {
            copy(
                totalDentro = totalDentro + 1,
                totalVisitas = totalVisitas + 1
            )
        }
        else this

    fun decrementar(): SalaModel =
        if (puedeDecrementar) copy(totalDentro = totalDentro - 1)
        else this

    fun restablecer(): SalaModel = SalaModel()
}
