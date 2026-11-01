package com.example.estado_claire.logic

data class User(
    val nom: String = "",
    val mail: String = "",
    val edad: Int? = null,
    val taller: Taller = Taller.NINGUNO
)
