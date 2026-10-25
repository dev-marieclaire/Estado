package com.example.estado_claire.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalcButton(modifier: Modifier = Modifier, text: String = "", onclick: () -> Unit = {})
{
    Box(modifier = modifier.padding(8.dp))
    {
        Button(
            onClick = onclick,
            modifier = modifier
                .fillMaxSize()
                .border(
                    1.dp, Color.White,
                    RoundedCornerShape(100.dp)
                ),
            colors = ButtonDefaults.buttonColors(Color(0xFFFCD8D8))
        )
        { Text(text, fontSize = 24.sp, fontWeight = FontWeight(200), color = Color(0xFF1A1A1A)) }
    }
}

@Preview
@Composable
fun PreviewCalcButton()
{
    CalcButton(Modifier.fillMaxWidth().height(48.dp), "a")
}