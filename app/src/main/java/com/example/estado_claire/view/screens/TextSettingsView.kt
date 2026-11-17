package com.example.estado_claire.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.estado_claire.model.FontType
import com.example.estado_claire.model.Spacing
import com.example.estado_claire.model.TextSettingsModel
import com.example.estado_claire.viewmodel.TextSettingsViewModel

@Composable
fun TextSettingsView(
    modifier: Modifier = Modifier,
    viewModel: TextSettingsViewModel = viewModel()
) {
    val settings by viewModel.settings.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Separación uniforme entre bloques
    ) {
        Text("Ajustes de letra", style = MaterialTheme.typography.titleMedium)

        // 1. Usamos las constantes del companion object del modelo en lugar de valores "quemados"
        Slider(
            value = settings.fontSize,
            onValueChange = { viewModel.changeFontSize(it) },
            valueRange = TextSettingsModel.MIN_FONT_SIZE..TextSettingsModel.MAX_FONT_SIZE
        )
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("A", fontSize = TextSettingsModel.MIN_FONT_SIZE.sp)
            Text("A", fontSize = TextSettingsModel.MAX_FONT_SIZE.sp)
        }

        Text("Interlineado", style = MaterialTheme.typography.titleMedium)
        Row(Modifier.fillMaxWidth()) {
            Spacing.entries.forEach { option ->
                Row(
                    Modifier
                        .weight(1f)
                        .height(56.dp)
                        .selectable(
                            selected = (settings.spacing == option),
                            onClick = { viewModel.changeSpacing(option) },
                            role = Role.RadioButton
                        )
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically // Centrado vertical del RadioButton
                ) {
                    RadioButton(
                        selected = (settings.spacing == option),
                        onClick = null // Null porque el Row padre maneja el clic
                    )
                    Text(
                        text = option.label,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 8.dp),
                        fontSize = 12.sp
                    )
                }
            }
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Modo Oscuro", style = MaterialTheme.typography.titleMedium)
            Switch(
                checked = settings.obscureMode,
                onCheckedChange = { viewModel.toggleObscureMode() }
            )
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Fuente", style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = settings.fontType == FontType.SANS,
                    onClick = { viewModel.changeFontType(FontType.SANS) },
                    label = { Text("Sans") }
                )
                FilterChip(
                    selected = settings.fontType == FontType.SERIF,
                    onClick = { viewModel.changeFontType(FontType.SERIF) },
                    label = { Text("Serif") }
                )
            }
        }

        // Vista Previa
        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Vista Previa", style = MaterialTheme.typography.titleMedium)
            Box(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    // 2. Movimos el background y el padding al Box para que el Text sea independiente
                    .background(color = if (settings.obscureMode) Color.Black else Color.White)
                    .padding(24.dp)
            ) {
                Text(
                    text = "Era una noche tranquila en el pueblo cuando las campanas comenzaron a sonar, y nadie supo por qué.",
                    // 3. Usamos la extensión .sp de Compose (mucho más limpio que TextUnit)
                    fontSize = settings.fontSize.sp,
                    letterSpacing = settings.spacing.multiplier.sp,
                    fontFamily = if (settings.fontType == FontType.SERIF) FontFamily.Serif else FontFamily.SansSerif,
                    color = if (settings.obscureMode) Color.White else Color.Black,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTextSettings() {
    MaterialTheme {
        Surface { TextSettingsView() }
    }
}
