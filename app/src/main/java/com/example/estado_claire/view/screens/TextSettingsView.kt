package com.example.estado_claire.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.estado_claire.model.FontType
import com.example.estado_claire.model.Spacing
import com.example.estado_claire.viewmodel.TextSettingsViewModel
import kotlin.ranges.rangeTo

@Composable
fun TextSettingsView(
    modifier: Modifier = Modifier,
    viewModel: TextSettingsViewModel = viewModel()
)
{
    val settings by viewModel.settings.collectAsStateWithLifecycle()

    Column(modifier.fillMaxWidth().padding(16.dp))
    {
        Text("Ajustes de letra")
        Slider(
            settings.fontSize,
            onValueChange = { viewModel.changeFontSize(it) },
            valueRange = 14f..32f)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
        {
            Text("A", fontSize = 14.sp)
            Text("A", fontSize = 32.sp)
        }

        Text("Interlineado")
        Row(Modifier.fillMaxWidth())
        {
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
                        .padding(8.dp)
                )
                {
                    RadioButton(
                        selected = (settings.spacing == option),
                        onClick = null
                    )
                    Text(
                        text = option.label,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp),
                        fontSize = 12.sp
                    )
                }
            }
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically)
        {
            Text("Modo Oscuro")
            Switch(settings.obscureMode, onCheckedChange = { viewModel.toggleObscureMode() })
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically)
        {
            Text("Fuente")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp))
            {
                FilterChip(
                    selected = settings.fontType == FontType.SANS,
                    onClick  = { viewModel.changeFontType(FontType.SANS) },
                    label    = { Text("Sans") }
                )
                FilterChip(
                    selected = settings.fontType == FontType.SERIF,
                    onClick  = { viewModel.changeFontType(FontType.SERIF) },
                    label    = { Text("Serif") }
                )
            }
        }

        Column(
            Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            Arrangement.spacedBy(8.dp)
        )
        {
            Text("Vista Previa")
            Box(Modifier.fillMaxSize())
            {
                Text(
                    "Era una noche tranquila en el pueblo cuando las campanas comenzaron a sonar, y nadie supo por qué.",
                    fontSize = TextUnit(settings.fontSize, TextUnitType.Sp),
                    letterSpacing = TextUnit(settings.spacing.multiplier, TextUnitType.Sp),
                    fontFamily =
                        if(settings.fontType == FontType.SERIF) FontFamily.Serif
                        else FontFamily.SansSerif,
                    color = if (settings.obscureMode == true) Color.White
                    else Color.Black,
                    modifier = Modifier
                        .fillMaxSize(1f)
                        .background(
                            color = if (settings.obscureMode == true) Color.Black
                            else Color.White
                        )
                        .padding(24.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewTextSettings()
{
    Surface {
        TextSettingsView(Modifier.fillMaxSize())
    }
}
