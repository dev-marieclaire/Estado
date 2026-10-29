package com.example.estado_claire.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.estado_claire.logic.Interlineado

@Composable
fun RadioButtonSection(
    modifier: Modifier = Modifier,
    title: String,
    selected: Interlineado,
    onSelected: (Interlineado) -> Unit
)
{
    Column(modifier)
    {
        Text(title)
        Row(Modifier.fillMaxWidth())
        {
            Interlineado.entries.forEach { option ->
                Row(
                    Modifier
                        .weight(1f)
                        .height(56.dp)
                        .selectable(
                            selected = selected == option,
                            onClick = { onSelected(option) },
                            role = Role.RadioButton
                        )
                        .padding(8.dp)
                )
                {
                    RadioButton(
                        selected = selected == option,
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
fun PreviewRadioButtonSection()
{
    Surface()
    {
        var state by rememberSaveable { mutableStateOf(Interlineado.NORMAL) }
        RadioButtonSection(
            modifier = Modifier.fillMaxWidth(),
            title = "title",
            selected = state,
            onSelected = { state = it }
        )
    }
}