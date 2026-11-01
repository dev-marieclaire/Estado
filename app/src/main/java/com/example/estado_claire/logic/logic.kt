package com.example.estado_claire.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

// En este archivo va la lógica que es específica a una práctica.

enum class Taller(val label: String, val cupo: Int)
{
    NINGUNO("Ninguno", -1),
    FOTOGRAFIA("Fotografía", 6),
    MUSICA("Música", 6)
}
