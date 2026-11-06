package com.example.estado_claire.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.estado_claire.components.AmountAugmentation
import com.example.estado_claire.components.Header

enum class Producto(
    val nom: String,
    val price: Float
)
{
    Tortillas("Tortillas 1 Kg", 24.0f),
    Leche("Leche 1 L", 27.5f),
    Huevo("Huevo 12 pz", 52.0f),
    Frijol("Frijol 1 Kg", 38.0f),
}

data class Canasta(
    val producto: Producto,
    val cantidad: Int
)

@Composable
fun InicioScreen(modifier: Modifier = Modifier)
{
    val carrito = remember { mutableStateListOf(
        Canasta(Producto.Tortillas, 0),
        Canasta(Producto.Leche, 0),
        Canasta(Producto.Huevo, 0),
        Canasta(Producto.Frijol, 0)
    ) }

    val sumatoriaArticulos: Int = carrito.sumOf { it.cantidad }

    val sumatoriaTotal: Float = carrito.fold(0f) { sum, item ->
        sum + item.producto.price * item.cantidad
    }

    val descuento = if (sumatoriaTotal >= 200f) sumatoriaTotal * 0.1f else 0f
    val totalDescontado = sumatoriaTotal - descuento

    val envio = when {
        totalDescontado >= 150f -> 0f
        sumatoriaArticulos == 0 -> 0f
        else -> 25f
    }

    val sumatoria = totalDescontado + envio

    Column(modifier.padding(16.dp), Arrangement.spacedBy(16.dp))
    {
        Header(modifier = Modifier.fillMaxWidth(), "Tienda Don Pepe")

        carrito.forEachIndexed()
        { index, item ->
            AmountAugmentation(
                modifier = Modifier.fillMaxWidth(),
                item.producto.nom,
                item.producto.price.toString(),
                item.cantidad,
                { new ->
                    if (new in 0..10) {
                        carrito[index] = item.copy(cantidad = new)
                    }
                }
            )
        }

        Column(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0x11333333))
                .padding(16.dp)
        )
        {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
            {
                Text("Artículos", fontSize = 20.sp)
                Text(sumatoriaArticulos.toString(), fontSize = 20.sp)
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
            {
                Text("Subtotal", fontSize = 20.sp)
                Text("\$ ${sumatoriaTotal.toString()}", fontSize = 20.sp)
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
            {
                Text("Descuento 10%", fontSize = 20.sp)
                Text("-\$ ${descuento.toString()}", fontSize = 20.sp, color = Color(0xFF00AA44))
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
            {
                Text("Envio", fontSize = 20.sp)

                Text(
                    when {
                        totalDescontado >= 150f -> "GRATIS"
                        else -> "\$ ${envio.toString()}"
                    },
                    fontSize = 20.sp,
                    color = Color(0xFF00AA44)
                )
            }

            Spacer(Modifier.padding(8.dp))
            Box(Modifier.fillMaxWidth().height(1.dp).background(Color.Black))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
            {
                Text("TOTAL", fontSize = 20.sp, fontWeight = FontWeight(600))
                Text("\$ ${sumatoria.toString()}", fontSize = 24.sp)
            }
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp))
        {
            OutlinedButton(
                {
                    carrito.forEachIndexed()
                    { index, canasta ->
                        carrito[index] = canasta.copy(cantidad = 0)
                    }
                },
                enabled = sumatoriaArticulos > 0
            ) { Text("Vaciar") }

            Button({},
                modifier = Modifier.weight(1f),
                enabled = sumatoriaArticulos > 0
            ) { Text("Confirmar pedido") }
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
