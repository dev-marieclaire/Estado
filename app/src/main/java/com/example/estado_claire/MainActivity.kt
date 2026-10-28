package com.example.estado_claire

import com.example.estado_claire.screens.InicioScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.estado_claire.ui.theme.EstadoClaireTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EstadoClaireTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        InicioScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
