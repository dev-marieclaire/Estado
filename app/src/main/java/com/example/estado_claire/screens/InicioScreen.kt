package com.example.estado_claire

import android.graphics.fonts.Font
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.estado_claire.components.AmountAugmentation
import com.example.estado_claire.components.Header
import com.example.estado_claire.components.LabeledSwitch
import com.example.estado_claire.components.SliderSection
import com.example.estado_claire.components.StatisticLine
import com.example.estado_claire.logic.Tip
import kotlin.plus


@Composable
fun InicioScreen(modifier: Modifier = Modifier)
{
    Column(modifier.padding(16.dp))
    {
        Header(Modifier.fillMaxWidth(), "Cuenta dividida")

        var montoState = rememberTextFieldState()

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            state = montoState,
            placeholder = { Text("$") },
            label = { Text("Monto de la cuenta") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            lineLimits = TextFieldLineLimits.SingleLine
        )

        val monto = montoState.text.toString()
        val montoFloat = monto.toFloatOrNull()?:0.0f

        var selectedTip by rememberSaveable { mutableStateOf(Tip.Low) }
        var customTip by rememberSaveable { mutableStateOf(0f) }

        val tipAmount = if (selectedTip == Tip.Other) customTip else selectedTip.amount

        if (selectedTip != Tip.Other)
        {
            Text("Propina")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp))
            {
                Tip.entries.forEach()
                { tip ->
                    FilterChip(
                        selected = selectedTip == tip,
                        onClick = { selectedTip = tip },
                        label = { Text(tip.label) }
                    )
                }
            }
        }
        else
        {
            SliderSection(
                Modifier.fillMaxWidth(),
                "Propina",
                customTip,
                { customTip = it },
                valueRange = 0f..0.3f,
                initialValue = { m, t, v -> Text("0%", m, fontSize = 24.sp) },
                { m, t, v -> Text("30%", m, fontSize = 24.sp) },
            )

            Button(onClick = { selectedTip = Tip.Low }) {
                Text("Usar propina sugerida")
            }
        }

        var totalPersonas by rememberSaveable { mutableStateOf(1) }
        AmountAugmentation(
            Modifier.fillMaxWidth(),
            "Personas",
            value = totalPersonas,
            onValueChange = {
                if (it >= 1 && it <= 10) totalPersonas = it
            }
        )

        var ceil by rememberSaveable { mutableStateOf(false) }
        LabeledSwitch(
            Modifier.fillMaxWidth(),
            "Redondear por persona",
            ceil,
            { ceil = it}
        )

        val totalPropina = montoFloat * tipAmount
        val totalConPropina = montoFloat + totalPropina
        val totalPorPersona = if (totalPersonas > 0) {
            if (ceil) Math.ceil(totalConPropina.toDouble() / totalPersonas.toDouble()).toFloat()
            else totalConPropina / totalPersonas
        }
        else 0.0f

        Column(
            Modifier.fillMaxWidth()
                .background(Color(0x28000000))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            val labelPropina = if (selectedTip == Tip.Other) "${(customTip * 100).toInt()}%" else selectedTip.label

            StatisticLine(
                Modifier.fillMaxWidth(),
                labelPropina,
                data = totalPropina.toString()
            )

            StatisticLine(
                Modifier.fillMaxWidth(),
                "Total con propina",
                data = totalConPropina.toString()
            )

            StatisticLine(
                Modifier.fillMaxWidth(),
                "Total con propina",
                data = totalConPropina.toString()
            )

            Box(Modifier.fillMaxWidth().height(1.dp).background(Color.Black))

            Text(
                "Cada quien paga",
                Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                "\$${totalPorPersona}",
                Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFF22BB44)
            )
            Text(
                if (totalPersonas >= 2) "entre ${totalPersonas} personas"
                else "entre una persona",
                Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
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
