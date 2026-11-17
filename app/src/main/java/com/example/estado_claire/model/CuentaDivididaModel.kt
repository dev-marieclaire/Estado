package com.example.estado_claire.model

import kotlin.math.roundToInt

enum class Propina(val amount: Float) {
    Small(10f),
    Mid(15f),
    Hi(20f),
    Custom(0f)
}

val PROPINA_MIN = 0f
val PROPINA_MAX = 30f

data class CuentaDivididaModel(
    val monto: Float = 0f,
    val propina: Propina = Propina.Hi,
    val customPropinaPercentage: Float = 0f,
    val personas: Int = 1,
    val redondear: Boolean = true
) {
    companion object {
        val TOTAL_PERSONAS_MIN = 1
        val TOTAL_PERSONAS_MAX = 20
    }

    // Si es Custom usa el porcentaje del slider, si no, el del Enum
    val propinaPercentage: Float
        get() = if (propina == Propina.Custom) customPropinaPercentage
        else propina.amount

    val totalPropina: Float
        get() = monto * (propinaPercentage / 100f)

    val totalConPropina: Float
        get() = monto + totalPropina

    val totalDividido: Float
        get() = if (personas > 0) totalConPropina / personas else 0f

    val total: Float
        get() = if (redondear) totalDividido.roundToInt().toFloat()
        else totalDividido

    val sobranteRedondeo: Float
        get() = if (redondear) (total * personas) - totalConPropina else 0f

    val puedeAgregar: Boolean
        get() = personas < TOTAL_PERSONAS_MAX

    val puedeRemover: Boolean
        get() = personas > TOTAL_PERSONAS_MIN

    fun setMonto(monto: Float) = copy(monto = monto)
    fun setPropina(propina: Propina) = copy(propina = propina)
    fun setCustomPropina(propina: Float) = copy(customPropinaPercentage = propina.coerceIn(PROPINA_MIN, PROPINA_MAX))
    fun addPersona() = if (puedeAgregar) copy(personas = personas + 1) else this
    fun removePersona() = if (puedeRemover) copy(personas = personas - 1) else this
    fun setRedondeo(round: Boolean) = copy(redondear = round)
}