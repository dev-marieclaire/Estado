package com.example.estado_claire.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.estado_claire.logic.TipoFuente

@Composable
fun FilterChipSection(
    modifier: Modifier = Modifier,
    title: String,
    selected: TipoFuente,
    onSelect: (TipoFuente) -> Unit
) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Text(title)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TipoFuente.entries.forEach { option ->
                FilterChip(
                    selected = selected == option,
                    onClick  = { onSelect(option) },
                    label    = { Text(option.label) }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewFilterChipSection()
{
    Surface()
    {
        var state by rememberSaveable { mutableStateOf(TipoFuente.SANS) }
        FilterChipSection(
            Modifier.fillMaxWidth(),
            "Title",
            state,
            { state = it }
        )
    }
}
