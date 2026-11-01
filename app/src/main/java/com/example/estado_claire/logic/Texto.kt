package com.example.estado_claire.logic

enum class Interlineado(val label: String, val multiplier: Float)
{
    COMPACTO("Compacto", 1.0f),
    NORMAL("Normal", 1.3f),
    AMPLIO("Amplio", 1.6f)
}

enum class TipoFuente(val type: Boolean, val label: String)
{
    SANS(false, "Sans"),
    SERIF(true, "Serif")
}

data class TextoEstado(
    val tamanoFuente: Float = 14.0f,
    val tamanoInterlineado: Interlineado = Interlineado.NORMAL,
    val tipoFuente: TipoFuente = TipoFuente.SANS,
    val modoOscuro: Boolean = false
)
