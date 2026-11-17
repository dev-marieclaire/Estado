package com.example.estado_claire.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.estado_claire.model.PROPINA_MAX
import com.example.estado_claire.model.PROPINA_MIN
import com.example.estado_claire.model.Propina
import com.example.estado_claire.view.components.Header
import com.example.estado_claire.viewmodel.CuentaDivididaViewModel
import java.util.Locale

@Composable
fun CuentaDivididaView(
    modifier: Modifier = Modifier,
    viewModel: CuentaDivididaViewModel = viewModel()
) {
    val cuenta by viewModel.cuenta.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    )
    {
        Header(
            Modifier.fillMaxWidth(),
            { Text("Cuenta dividida", fontSize = 24.sp) }
        )

        var montoTextState by rememberSaveable { mutableStateOf("") }

        OutlinedTextField(
            value = montoTextState,
            onValueChange = { nuevo ->
                if (nuevo.matches(Regex("^\\d*\\.?\\d{0,2}$"))) {
                    montoTextState = nuevo
                    nuevo.toFloatOrNull()?.let(viewModel::setMonto) ?: viewModel.setMonto(0f)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Monto de la cuenta") },
            placeholder = { Text("$ 0.00") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        Text("Propina", fontWeight = FontWeight.Medium)

        // Fila de Chips
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Propina.entries.forEach()
            { item ->
                FilterChip(
                    selected = cuenta.propina == item,
                    onClick = { viewModel.setPropina(item) },
                    label = { Text(if (item != Propina.Custom) "${item.amount.toInt()} %" else "Otro") }
                )
            }
        }

        if (cuenta.propina == Propina.Custom)
        {
            Column(Modifier.fillMaxWidth())
            {
                Text("Porcentaje personalizado: ${cuenta.customPropinaPercentage.toInt()} %")
                Slider(
                    value = cuenta.customPropinaPercentage,
                    onValueChange = { viewModel.setCustomPropina(it) },
                    valueRange = PROPINA_MIN..PROPINA_MAX
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text("Personas", Modifier.weight(1f), fontWeight = FontWeight.Medium)
            OutlinedIconButton(
                onClick = { viewModel.removePersona() },
                enabled = cuenta.puedeRemover
            ) { Text("-") }

            Text(cuenta.personas.toString(), modifier = Modifier.padding(horizontal = 8.dp))

            OutlinedIconButton(
                onClick = { viewModel.addPersona() },
                enabled = cuenta.puedeAgregar
            ) { Text("+") }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text("Redondear por persona", fontWeight = FontWeight.Medium)
            Switch(
                onCheckedChange = { viewModel.setRedondeo(it) },
                checked = cuenta.redondear
            )
        }


        // Tarjeta de Resumen
        Column(modifier = Modifier.fillMaxWidth().background(Color(0x11333333)))
        {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                // Propina
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
                {
                    Text("Propina (${cuenta.propinaPercentage.toInt()} %)")
                    Text(cuenta.totalPropina.toCurrency())
                }
                Spacer(Modifier.height(8.dp))

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
                {
                    Text("Total con propina", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(cuenta.totalConPropina.toCurrency())
                }

                Text("Cada quien paga")
                Text(
                    cuenta.total.toCurrency(),
                    fontSize = 48.sp
                )

                Text("entre ${cuenta.personas} personas")

                if (cuenta.redondear)
                {
                    Spacer(Modifier.height(8.dp))
                    val sobrante = cuenta.sobranteRedondeo
                    val textoSobrante = if (sobrante >= 0f) "Sobrante" else "Faltante"

                    Text(
                        "$textoSobrante por redondeo: ${kotlin.math.abs(sobrante).toCurrency()}",
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

fun Float.toCurrency(): String {
    return String.format(Locale.US, "$ %,.2f", this)
}

@Preview(showBackground = true)
@Composable
fun PreviewCuentaDivididaView() {
    MaterialTheme {
        Surface { CuentaDivididaView() }
    }
}