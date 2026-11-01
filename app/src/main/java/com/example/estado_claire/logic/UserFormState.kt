package com.example.estado_claire.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class UserFormState(initialUser: User = User())
{
    var user by mutableStateOf(initialUser)
        private set

    var edadText by mutableStateOf(initialUser.edad?.toString()?:"")
        private set

    var nomError by mutableStateOf<String?>(null)
    var mailError by mutableStateOf<String?>(null)
    var edadError by mutableStateOf<String?>(null)

    var tallerError by mutableStateOf<String?>(null)

    fun updateNom(nouveauNom: String)
    {
        user = user.copy(nom = nouveauNom)
        if (nomError != null) nomError = null // Limpia los errores al escribir.
    }

    fun updateMail(nouveauMail: String)
    {
        user = user.copy(mail = nouveauMail)
        if (mailError != null) mailError = null
    }

    fun updateEdad(nouveauAge: String)
    {
        if (nouveauAge != "")
        {
            user = user.copy(edad = nouveauAge.toInt())
            edadText = user.edad.toString()
        }
        else
        {
            user = user.copy(edad = null)
            edadText = ""
        }

        if (edadError != null) edadError = null
    }

    fun updateTaller(nouveauTaller: Taller)
    {
        user = user.copy(taller = nouveauTaller)
        if (tallerError != null) tallerError = null
    }

    fun validate(): Boolean
    {
        var isValid = true

        if (user.nom.isBlank())
        {
            nomError = "El nombre es obligatorio."
            isValid = false
        }

        if (user.nom.length > 3)
        {
            nomError = "El nombre debe contener tres carácteres mínimo."
            isValid = false
        }

        if (!user.mail.contains("@") || user.mail.isBlank())
        {
            mailError = "Ingresa un correo electrónico válido."
            isValid = false
        }

        if (user.edad == null || user.edad!! <= 0)
        {
            edadError = "Ingresa una edad válida."
            isValid = false
        }

        if (user.taller == Taller.NINGUNO)
        {
            tallerError = "Seleccione un taller."
            isValid = false
        }

        return isValid
    }
}