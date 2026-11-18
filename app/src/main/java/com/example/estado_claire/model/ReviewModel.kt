package com.example.estado_claire.model

enum class RatingLevel(val text: String, val score: Int)
{
    Ninguno("", 0),
    Malo("Malo", 1),
    Regular("Regular", 2),
    Bueno("Bueno", 3),
    Buenisimo("Muy bueno", 4),
    Excelete("Excelente", 5)
}

data class RatingCategory(
    val nom: String,
    val rating: RatingLevel = RatingLevel.Ninguno
)

object ReviewConstants
{
    const val MIN_COMMENT_LENGTH = 45
    const val MAX_COMMENT_LENGTH = 200
}