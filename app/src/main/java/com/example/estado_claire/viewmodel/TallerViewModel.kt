package com.example.estado_claire.viewmodel

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.lifecycle.ViewModel
import com.example.estado_claire.model.Taller
import com.example.estado_claire.model.TallerModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private val EMAIL_REGEX = Regex(
    "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
)

class TallerViewModel : ViewModel()
{
    var _taller = MutableStateFlow<TallerModel>(TallerModel())
        private set

    val taller: StateFlow<TallerModel> = _taller.asStateFlow()

    fun changeTaller(taller: Taller)
    { _taller.value = _taller.value.changeTaller(taller) }

    fun clearAllErrors()
    { _taller.value.clearAllErrors() }

    fun validateForm(): Boolean
    {
        _taller.value.clearAllErrors()
        val current = _taller.value

        val nom  = current.nomTextField.text.toString().trim()
        val mail = current.mailTextField.text.toString().trim()
        val ageText = current.ageTextField.text.toString().trim()
        val age  = ageText.toIntOrNull()

        val nomError = when {
            nom.isEmpty()    -> "El nombre no debe estar vacío"
            nom.length < 3   -> "El nombre debe contener al menos 3 caracteres"
            else             -> null
        }

        val mailError = when {
            mail.isEmpty()             -> "El correo no debe estar vacío"
            !EMAIL_REGEX.matches(mail) -> "El correo debe tener un formato válido (ejemplo@dominio.com)"
            else                       -> null
        }

        val ageError = when {
            ageText.isEmpty() -> "La edad no debe estar vacía"
            age == null       -> "La edad debe ser un número"
            age < 12          -> "La edad mínima es 12 años"
            age > 99          -> "La edad máxima es 99 años"
            else              -> null
        }

        val tallerError =
            if (current.tallerDropdownField == Taller.Ninguno)
                "Seleccione un taller"
            else null

        _taller.update {
            it.copy(
                nomTextFieldError       = nomError,
                mailTextFieldError      = mailError,
                ageTextFieldError       = ageError,
                tallerDropdownFieldError = tallerError,
            )
        }

        return nomError == null && mailError == null && ageError == null && tallerError == null
    }
}