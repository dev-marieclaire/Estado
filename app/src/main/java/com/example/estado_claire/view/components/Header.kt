package com.example.estado_claire.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Header(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    subtitle: @Composable () -> Unit = {}
)
{
    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp))
    {
        title()
        subtitle()
    }
}

@Preview
@Composable
fun PreviewHeader()
{
    Surface()
    {
        Header(
            modifier = Modifier.fillMaxWidth(),
            { Text("Título") },
            { Text("Subtítulo") }
        )
    }
}
