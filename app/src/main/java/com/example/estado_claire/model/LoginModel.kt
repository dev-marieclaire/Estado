package com.example.estado_claire.model

data class LoginModel(
    val mail: String = "",
    val passwd: String = ""
) {
    companion object {
        const val VALID_MAIL: String = "paciente@vida.mx"
        const val VALID_PASSWD: String = "Vida2025"
    }
}
