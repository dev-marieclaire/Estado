package com.example.estado_claire.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MiCard(modifier: Modifier = Modifier, color: Color, string: String)
{
    Box(modifier = modifier.fillMaxSize().background(color = color))
    {
        Text(string, Modifier.padding(8.dp).fillMaxWidth(), fontSize = 32.sp, textAlign = TextAlign.Center)
    }
}

@Preview
@Composable
fun PreviewMiCard()
{
    Surface()
    {
        MiCard(Modifier.fillMaxWidth().height(64.dp), Color.Cyan, "nepomuseno")
    }
}