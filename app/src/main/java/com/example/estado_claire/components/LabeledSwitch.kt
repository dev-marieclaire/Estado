package com.example.estado_claire.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LabeledSwitch(
    modifier: Modifier = Modifier,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
)
{
    Row(
        modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Text(title)
        Switch(checked, onCheckedChange = onCheckedChange)
    }
}

@Preview
@Composable
fun PreviewLabeledSwitch()
{
    Surface()
    {
        var check by rememberSaveable { mutableStateOf(false) }
        LabeledSwitch(
            Modifier.fillMaxWidth(),
            title = "title",
            checked = check,
            onCheckedChange = { check = it }
        )
    }
}