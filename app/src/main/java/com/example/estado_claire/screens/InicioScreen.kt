package com.example.estado_claire

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.estado_claire.components.Header

enum class CategoriaGasto(val nom: String)
{
    None("Ninguno"),
    Comida("Comida"),
    Transporte("Transporte"),
    Renta("Renta"),
    Ocio("Ocio"),
    Otros("Otro")
}

data class Gasto(
    var concepto: String,
    var monto: Float,
    var categoria: CategoriaGasto
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(modifier: Modifier = Modifier)
{
    val amountState   = rememberTextFieldState()
    val newWasteName  = rememberTextFieldState()
    val newWasteMonto = rememberTextFieldState()

    val wasteList = remember { mutableStateListOf<Gasto>() }
    val category  = rememberSaveable { mutableStateOf<CategoriaGasto>(CategoriaGasto.None) }
    var selectedCategory by rememberSaveable { mutableStateOf(CategoriaGasto.None) }

    val wasteTotal: Double = wasteList.sumOf { it.monto.toDouble() }
    val amount: Double = amountState.text.toString().toDoubleOrNull() ?: 0.0

    val fraction: Float =
        if (amount > 0.0) (wasteTotal / amount).toFloat() else 0f

    val barColor = when {
        fraction >= 1f   -> Color.Red
        fraction >= 0.8f -> Color.Yellow
        else             -> Color.Green
    }

    val boxColor = when {
        fraction >= 1f   -> Color(0x33FF2200)
        fraction >= 0.8f -> Color(0x33CC7711)
        else             -> Color(0x3300FF33)
    }

    val mayorGasto = wasteList.maxByOrNull { it.monto }

    Column(
        modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp))
    {
        Header(Modifier.fillMaxWidth(), "Mi presupuesto")

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            state = amountState,
            label = { Text("Ingreso mensual") },
            placeholder = { Text("$") }
        )

        Box(
            Modifier
                .fillMaxWidth()
                .background(boxColor)
                .padding(8.dp)
        )
        {
            Column(Modifier.fillMaxWidth())
            {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
                {
                    Column(Modifier.weight(1f))
                    {
                        Text("Gastado")
                        Text("\$${wasteTotal}")
                    }

                    Column(Modifier.weight(1f))
                    {
                        Text("Saldo")
                        Text("\$${amount - wasteTotal}")
                    }
                }

                var progress by rememberSaveable { mutableStateOf(0.4f) }

                LinearProgressIndicator(
                    progress = { fraction.coerceIn(0f, 1f) },
                    modifier = Modifier.fillMaxWidth().height(16.dp),
                    color = barColor,
                )
                Text(
                    "${(fraction * 100).toInt()}% usado · Mayor gasto: "
                    + (mayorGasto?.let { "$${it.monto}" } ?: "—")
                )
            }
        }

        if (fraction >= 0.8f)
        {
            Text(
                "Cuidado: pasaste del 80%",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0x44FF9911))
                    .padding(8.dp)
            )
        }

        Text("Nuevo gasto")

        OutlinedTextField(
            newWasteName,
            modifier.fillMaxWidth(),
            label = { Text("Concepto") },
            placeholder = { Text("Nombre de concepto.") }
        )

        Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp))
        {
            OutlinedTextField(
                newWasteMonto,
                modifier = Modifier.weight(1f),
                label = { Text("Monto") },
                placeholder = { Text("$") }
            )

            var expansion by rememberSaveable { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expansion,
                onExpandedChange = { expansion = it },        // usa el param, no !expansion
                modifier = Modifier.weight(1f),
            )
            {
                OutlinedTextField(
                    value = selectedCategory.nom,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoría") },
                    placeholder = { Text("Seleccione") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expansion)
                    },
                    modifier = Modifier
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth(),
                )
                ExposedDropdownMenu(
                    expanded = expansion,
                    onDismissRequest = { expansion = false },
                ) {
                    CategoriaGasto.entries.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion.name) },
                            onClick = {
                                selectedCategory = opcion
                                expansion = false
                            },
                        )
                    }
                }
            }
        }

        Button(
        {
            if (
                newWasteName.text.toString().isNotBlank() &&
                newWasteMonto.text.toString().isNotBlank() &&
                selectedCategory != CategoriaGasto.None
            )
            {
                wasteList.add(
                    Gasto(
                        newWasteName.text.toString(),
                        newWasteMonto.text.toString().toFloatOrNull() ?: 0f,
                        selectedCategory
                    )
                )
            }
        },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Agregar gasto") }

        LazyColumn()
        {
            items(wasteList)
            { item ->
                Row(modifier.fillMaxWidth())
                {
                    Column(Modifier.weight(1f))
                    {
                        Text(item.concepto)
                        Text(item.categoria.nom)
                    }
                    Text("-\$${item.monto.toString()}")

                    IconButton({ wasteList.remove(item) })
                    { Icon(imageVector = Icons.Outlined.Cancel, null) }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InicioPreview() {
    MaterialTheme { Surface(Modifier.fillMaxSize()) { InicioScreen() } }
}