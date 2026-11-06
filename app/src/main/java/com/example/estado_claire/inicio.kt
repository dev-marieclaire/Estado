package com.example.estado_claire

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun InicioScreen(modifier: Modifier)
{
    Column(modifier)
    {
        var amount = rememberTextFieldState()
        TextField(
            amount,
            modifier = Modifier.fillMaxWidth()
        )

        val amountNumber = amount.text.toString().toIntOrNull()?:0

        LazyColumn()
        {
            item {
                repeat(amountNumber) { Text("C", fontSize = 32.sp) }
            }
        }
    }
}

@Preview
@Composable
fun PreviewInicioScreen()
{
    Surface() {
        InicioScreen(Modifier.fillMaxSize())
    }
}