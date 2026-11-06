package com.example.estado_claire.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AmountAugmentation(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String = "",
    value: Int,
    onValueChange: (Int) -> Unit
)
{
    Column(Modifier.fillMaxWidth())
    {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp))
            {
                Text(
                    title,
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp
                )
                if (subtitle != "") Text(subtitle)
            }


            Row(verticalAlignment = Alignment.CenterVertically)
            {
                OutlinedIconButton(
                    modifier = Modifier
                        .height(32.dp)
                        .aspectRatio(1f),
                    onClick = { onValueChange(value - 1) },
                ) { Text("-") }

                Text(
                    value.toString(),
                    modifier = Modifier.width(32.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                )

                OutlinedButton(
                    modifier = Modifier
                        .height(32.dp)
                        .aspectRatio(1f),
                    onClick = { onValueChange(value + 1) },
                ) { Text("+", fontSize = 8.sp) }
            }
        }
        Box(Modifier.fillMaxWidth().height(1.dp).background(Color.Gray))
    }
}
