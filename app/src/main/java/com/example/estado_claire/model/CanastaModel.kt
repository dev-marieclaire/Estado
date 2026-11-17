package com.example.estado_claire.model

enum class Producto(val precio: Float, val unidad: String)
{
    Tortillas(24f, "1 kg"),
    Leche(27.5f, "1 L"),
    Huevo(52f, "12 pz"),
    Frijol(38f, "1 kg")
}

data class CanastaModel(val productos: List<Producto> = emptyList())
{
    companion object {
        const val MAX_POR_PRODUCTO   = 10
        const val UMBRAL_DESCUENTO   = 200f
        const val TASA_DESCUENTO     = 0.1f
        const val UMBRAL_ENVIO_GRATIS = 150f
        const val COSTE_ENVIO        = 25f
    }

    val totalArticulos: Int
        get() = productos.size

    val subtotal: Float
        get() = productos.fold(0f) { acc, p -> acc + p.precio }

    val tieneDescuento: Boolean
        get() = subtotal >= UMBRAL_DESCUENTO

    val descuento: Float
        get() = if (tieneDescuento) subtotal * TASA_DESCUENTO else 0f

    val tieneEnvioGratis: Boolean
        get() = (subtotal - descuento) >= UMBRAL_ENVIO_GRATIS

    val envio: Float
        get() = when {
            totalArticulos == 0 -> 0f
            tieneEnvioGratis -> 0f
            else -> COSTE_ENVIO
        }

    val total: Float
        get() = subtotal - descuento + envio

    fun vaciar(): CanastaModel = copy(productos = emptyList<Producto>())

    // Derivadas: nunca se almacenan
    fun cantidad(p: Producto): Int = productos.count { it == p }

    fun puedeAgregar(p: Producto): Boolean = cantidad(p) < MAX_POR_PRODUCTO
    fun puedeQuitar(p: Producto):  Boolean = cantidad(p) > 0

    // Operaciones inmutables
    fun agregar(p: Producto): CanastaModel =
        if (puedeAgregar(p)) copy(productos = productos + p) else this

    fun quitar(p: Producto): CanastaModel =
        if (puedeQuitar(p)) {
            val i = productos.indexOf(p)          // primer match, da igual cuál
            copy(productos = productos.toMutableList().apply { removeAt(i) })
        } else this
}