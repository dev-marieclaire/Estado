package com.example.estado_claire.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

// En este archivo va la lógica que es específica a una práctica.
enum class Tip(val label: String, val amount: Float, val selected: Boolean)
{
    Low("10%", 0.1f, false),
    Mid("15%", 0.15f, false),
    Hi("20%", 0.20f, true),
    Other("Otro", 0.3f, false)
}
