package com.example.estado_claire.model

import androidx.compose.foundation.text.input.TextFieldState

enum class Taller()
{
    Ninguno,
    Fotografia,
    Musica
}

private val EMAIL_REGEX = Regex(
    "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
)

class User(
    nom: String,
    mail: String,
    age: Int,
    taller: Taller,
) {
    init {
        require(nom.isNotBlank()) { "El nombre no debe estar vacío" }
        require(nom.trim().length >= 3) { "El nombre debe contener al menos 3 caracteres" }

        require(mail.isNotBlank()) { "El correo no debe estar vacío" }
        require(EMAIL_REGEX.matches(mail.trim())) {
            "El correo debe tener un formato válido"
        }

        require(age in 12..99) { "La edad debe estar entre 12 y 99 años" }

        require(taller != Taller.Ninguno) { "Seleccione un taller" }
    }
}

data class TallerModel(
    val nomTextField: TextFieldState = TextFieldState(),
    val nomTextFieldError: String? = null,
    val mailTextField: TextFieldState = TextFieldState(),
    val mailTextFieldError: String? = null,
    val ageTextField: TextFieldState = TextFieldState(),
    val ageTextFieldError: String? = null,
    val tallerDropdownField: Taller = Taller.Ninguno,
    val tallerDropdownFieldError: String? = null,
)
{
    fun clearNomError(): TallerModel = copy(nomTextFieldError = null)
    fun clearMailError(): TallerModel = copy(mailTextFieldError = null)
    fun clearAgeError(): TallerModel = copy(ageTextFieldError = null)
    fun clearTallerError(): TallerModel = copy(tallerDropdownFieldError = null)

    fun changeTaller(taller: Taller): TallerModel = copy(tallerDropdownField = taller)

    fun clearAllErrors()
    {
        clearNomError()
        clearMailError()
        clearAgeError()
        clearTallerError()
    }
}
