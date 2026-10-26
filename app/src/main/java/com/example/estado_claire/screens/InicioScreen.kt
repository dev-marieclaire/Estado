package com.example.estado_claire

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class Interlineado(val label: String, val multiplier: Float)
{
    COMPACTO("Compacto", 0.5f),
    NORMAL("Normal", 1f),
    AMPLIO("Amplio", 1.5f)
}

@Composable
fun InicioScreen(modifier: Modifier = Modifier)
{
    var font_size by rememberSaveable { mutableStateOf(0f) }

    var spacing_state by rememberSaveable { mutableStateOf(Interlineado.NORMAL) }
    val current_spacing = spacing_state.multiplier

    val spacing_options = Interlineado.entries

    Column(modifier.fillMaxWidth().padding(16.dp))
    {
        Text("Ajustes de letra")
        Slider(font_size, onValueChange = { font_size = it })
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
