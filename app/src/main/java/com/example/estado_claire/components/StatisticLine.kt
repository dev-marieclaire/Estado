package com.example.estado_claire.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatisticLine(
    modifier: Modifier = Modifier,
    mainText: String,
    subText: String = "",
    data: String
)
{
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        if (subText != "")
        {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp))
            {
                Text(mainText)
                Text(subText)
            }
        }
        else Text(mainText)
        Text(data)
    }
}