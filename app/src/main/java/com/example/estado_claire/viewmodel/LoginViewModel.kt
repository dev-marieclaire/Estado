package com.example.estado_claire.viewmodel

import androidx.lifecycle.ViewModel
import com.example.estado_claire.model.LoginModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LoginUiState(
    val model: LoginModel = LoginModel(),
    val showPassword: Boolean = false,
    val rememberMe: Boolean = false,
    val errorMsg: String? = null,
    val isSuccess: Boolean = false
)

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onMailChange(mail: String) {
        _uiState.update { it.copy(model = it.model.copy(mail = mail), errorMsg = null) }
    }

    fun onPassChange(passwd: String) {
        _uiState.update { it.copy(model = it.model.copy(passwd = passwd), errorMsg = null) }
    }

    fun toggleShowPassword() {
        _uiState.update { it.copy(showPassword = !it.showPassword) }
    }

    fun toggleRememberMe(remember: Boolean) {
        _uiState.update { it.copy(rememberMe = remember) }
    }

    fun attemptLogin() {
        val currentModel = _uiState.value.model
        val isValid = currentModel.mail.trim() == LoginModel.VALID_MAIL &&
                currentModel.passwd == LoginModel.VALID_PASSWD

        if (isValid) {
            _uiState.update { it.copy(errorMsg = null, isSuccess = true) }
        } else {
            _uiState.update { it.copy(errorMsg = "Correo o contraseña incorrectos", isSuccess = false) }
        }
    }

    // Buena práctica: resetear el estado de éxito después de navegar
    fun resetSuccess() {
        _uiState.update { it.copy(isSuccess = false) }
    }
}
