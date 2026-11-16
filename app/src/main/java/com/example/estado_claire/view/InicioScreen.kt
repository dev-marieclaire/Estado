package com.example.estado_claire.view.components.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.estado_claire.view.screens.Dashboard

@Composable
fun StartScreen(modifier: Modifier = Modifier)
{
    Dashboard(modifier)
}

@Preview
@Composable
fun PreviewStartScreen() {
    Surface { StartScreen(Modifier.fillMaxSize()) }
}