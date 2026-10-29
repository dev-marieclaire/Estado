package com.example.estado_claire.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun SliderSection(
    modifier: Modifier = Modifier,
    title: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    initialValue: @Composable (modifier: Modifier, text: String, value: Float) -> Unit = { m, t, v -> Text(t, m, fontSize = v.sp) },
    finalValue: @Composable (modifier: Modifier, text: String, value: Float) -> Unit = { m, t, v -> Text(t, m, fontSize = v.sp) }
)
{
    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp))
    {
        Text(title, fontSize = 24.sp)
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange
        )
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
        {
            initialValue(Modifier, "A", 14.0f)
            finalValue(Modifier, "A", 32.0f)
        }
    }
}

@Preview
@Composable
fun PreviewSliderSection()
{
    Surface()
    {
        var state by rememberSaveable { mutableStateOf(14f) }
        SliderSection(
            Modifier.fillMaxWidth(),
            "Slider",
            onValueChange = { state = it },
            value = state,
            valueRange = 14f..32f
        )
    }
}