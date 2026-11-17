package com.example.estado_claire.viewmodel

import androidx.lifecycle.ViewModel
import com.example.estado_claire.model.Taller
import com.example.estado_claire.model.TallerModel
import com.example.estado_claire.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TallerViewModel : ViewModel()
{
    private val _tallerState = MutableStateFlow(TallerModel())
    val tallerState: StateFlow<TallerModel> = _tallerState.asStateFlow()

    fun onNombreChange(nombre: String) = _tallerState.update()
    { it.copy(nombre = nombre, nombreError = null) }

    fun onEmailChange(email: String) = _tallerState.update()
    { it.copy(email = email, emailError = null) }

    fun onEdadChange(edad: String) = _tallerState.update()
    {
        if (edad.all { it.isDigit() }) it.copy(edad = edad, edadError = null)
        else it
    }

    fun onTallerChange(taller: Taller) = _tallerState.update()
    { it.copy(taller = taller, tallerError = null) }

    fun validateForm()
    {
        val current = _tallerState.value
        val age = current.edad.toIntOrNull()

        val nombreError = when {
            current.nombre.isBlank() -> "El nombre no debe estar vacío"
            current.nombre.trim().length < 3 -> "El nombre debe contener al menos 3 caracteres"
            else -> null
        }

        val emailError = when {
            current.email.isBlank() -> "El correo no debe estar vacío"
            !current.email.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) -> "Formato de correo inválido"
            else -> null
        }

        val edadError = when {
            current.edad.isBlank() -> "La edad no debe estar vacía"
            age == null -> "La edad debe ser un número"
            age < 12 || age > 99 -> "La edad debe estar entre 12 y 99 años"
            else -> null
        }

        val tallerError = if (current.taller == Taller.Ninguno) "Seleccione un taller" else null

        val hasErrors = nombreError != null || emailError != null || edadError != null || tallerError != null

        if (!hasErrors)
        {
            _tallerState.update()
            {
                it.copy(
                    nombreError = null, emailError = null, edadError = null, tallerError = null,
                    isSuccess = true
                )
            }
        }
        else
        {
            _tallerState.update()
            {
                it.copy(
                    nombreError = nombreError,
                    emailError = emailError,
                    edadError = edadError,
                    tallerError = tallerError
                )
            }
        }
    }

    fun resetSuccessState()
    { _tallerState.update { it.copy(isSuccess = false) } }
}