package com.example.estado_claire

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.then
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.estado_claire.components.MiCard


@Composable
fun InicioScreen(modifier: Modifier = Modifier) {
    var repeats by rememberSaveable { mutableStateOf("") }
    val r = repeats.toIntOrNull() ?: 0

    Column(modifier = modifier.fillMaxSize().padding(8.dp)) {
        TextField(
            value = repeats,
            onValueChange = { nuevo ->
                if (nuevo.all { it.isDigit() }) repeats = nuevo
            },
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(r) {
                MiCard(
                    modifier = Modifier.fillMaxWidth().height(64.dp),
                    Color.Black,
                    "C"
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}