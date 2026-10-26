package com.example.estado_claire

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.ranges.rangeTo

enum class Interlineado(val label: String, val multiplier: Float)
{
    COMPACTO("Compacto", 1.0f),
    NORMAL("Normal", 1.3f),
    AMPLIO("Amplio", 1.6f)
}

enum class FontType(val type: Boolean)
{
    SANS(false),
    SERIF(true)
}

@Composable
fun InicioScreen(modifier: Modifier = Modifier)
{
    var font_size by rememberSaveable { mutableStateOf(14f) }

    var spacing_state by rememberSaveable { mutableStateOf(Interlineado.NORMAL) }
    val current_spacing = spacing_state.multiplier

    val spacing_options = Interlineado.entries

    var obscure_mode by rememberSaveable { mutableStateOf(false) }

    var font_type by rememberSaveable { mutableStateOf(FontType.SANS.type) }

    Column(modifier.fillMaxWidth().padding(16.dp))
    {
        Text("Ajustes de letra")
        Slider(font_size, onValueChange = { font_size = it }, valueRange = 14f..32f)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
        {
            Text("A", fontSize = 14.sp)
            Text("A", fontSize = 32.sp)
        }

        Text("Interlineado")
        Row(Modifier.fillMaxWidth())
        {
            spacing_options.forEach { option ->
                Row(
                    Modifier
                        .weight(1f)
                        .height(56.dp)
                        .selectable(
                            selected = (spacing_state == option),
                            onClick = { spacing_state = option },
                            role = Role.RadioButton
                        )
                        .padding(8.dp)
                )
                {
                    RadioButton(
                        selected = (spacing_state == option),
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
            Switch(obscure_mode, onCheckedChange = { obscure_mode = it })
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically)
        {
            Text("Fuente")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp))
            {
                FilterChip(
                    selected = font_type == FontType.SANS.type,
                    onClick  = { font_type = FontType.SANS.type },
                    label    = { Text("Sans") }
                )
                FilterChip(
                    selected = font_type == FontType.SERIF.type,
                    onClick  = { font_type = FontType.SERIF.type },
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
                    fontSize = TextUnit(font_size, TextUnitType.Sp),
                    letterSpacing = TextUnit(current_spacing, TextUnitType.Sp),
                    fontFamily =
                        if(font_type == FontType.SERIF.type) FontFamily.Serif
                        else FontFamily.SansSerif,
                    color = if (obscure_mode == true) Color.White
                            else Color.Black,
                    modifier = Modifier
                        .fillMaxSize(1f)
                        .background(
                            color = if (obscure_mode == true) Color.Black
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
fun PreviewInicioScreen()
{
    Surface {
        InicioScreen(Modifier.fillMaxSize())
    }
}
