package com.example.estado_claire.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.estado_claire.model.Producto
import com.example.estado_claire.view.components.Header
import com.example.estado_claire.viewmodel.CanastaViewModel

@Composable
fun CanastaView(
    modifier: Modifier = Modifier,
    viewModel: CanastaViewModel = viewModel()
)
{
    val canasta by viewModel.canasta.collectAsStateWithLifecycle()

    Column(modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp))
    {
        Header(Modifier.fillMaxWidth(), { Text("Tienda Don Pepe", fontSize = 24.sp) })
        Producto.entries.forEach()
        { item ->
            key(item) {
                val cantidad = canasta.cantidad(item)

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Column(Modifier.weight(1f))
                    {
                        Text("${item.name} ${item.unidad}", fontSize = 18.sp, fontWeight = FontWeight(600))
                        Text("\$${item.precio} c/u")
                    }

                    OutlinedIconButton(
                        onClick = { viewModel.deleteProducto(item) },
                        enabled = canasta.puedeQuitar(item)
                    ) { Text("-") }

                    Text("$cantidad")

                    OutlinedIconButton(
                        onClick = { viewModel.addProducto(item) },
                        enabled = canasta.puedeAgregar(item)
                    ) { Text("+") }
                }
            }
        }

        Column(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0x11333333))
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text("Artículos", fontSize = 18.sp)
                Text(canasta.totalArticulos.toString(), fontSize = 18.sp)
            }

            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text("Subtotal", fontSize = 18.sp)
                Text(canasta.subtotal.toString(), fontSize = 18.sp)
            }

            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text("Descuento 10 %", fontSize = 18.sp)
                Text("-\$${canasta.descuento}", fontSize = 18.sp)
            }

            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text("Envío", fontSize = 18.sp)
                Text(
                    if (canasta.tieneEnvioGratis) "Gratis"
                    else canasta.envio.toString(),
                    fontSize = 18.sp
                )
            }

            Box(Modifier.fillMaxWidth().height(1.dp).background(Color(0xFF1A1A1A)))

            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text("Total", fontSize = 24.sp)
                Text(canasta.total.toString(), fontSize = 24.sp)
            }
        }

        Spacer(Modifier.weight(1f))

        Row(Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp))
        {
            OutlinedButton(onClick = { viewModel.vaciar() }, modifier = Modifier.weight(1f)) { Text("Vaciar") }
            Button(onClick = {}, modifier = Modifier.weight(2f)) { Text("Confirmar pedido") }
        }
    }
}

@Preview
@Composable
fun PreviewCanastaView()
{
    Surface()
    { CanastaView() }
}
