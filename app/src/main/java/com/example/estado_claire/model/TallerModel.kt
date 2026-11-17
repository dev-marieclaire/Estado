package com.example.estado_claire.model

enum class Taller(val label: String)
{
    Ninguno("Ninguno"),
    Fotografia("Fotografía"),
    Musica("Música")
}

private val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

class User(
    val nom: String,
    val mail: String,
    val age: Int,
    val taller: Taller,
)
{
    init {
        require(nom.trim().length >= 3) { "El nombre debe contener al menos 3 caracteres" }
        require(EMAIL_REGEX.matches(mail.trim())) { "El correo debe tener un formato válido" }
        require(age in 12..99) { "La edad debe estar entre 12 y 99 años" }
        require(taller != Taller.Ninguno) { "Seleccione un taller" }
    }
}

data class TallerModel(
    val nombre: String = "",
    val email: String = "",
    val edad: String = "",
    val taller: Taller = Taller.Ninguno,

    val nombreError: String? = null,
    val emailError: String? = null,
    val edadError: String? = null,
    val tallerError: String? = null,

    val isSuccess: Boolean = false
)