package com.example.estado_claire.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.estado_claire.view.components.Header
import com.example.estado_claire.viewmodel.LoginViewModel

@Composable
fun Login(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Efecto secundario para notificar a la pantalla padre (InicioScreen) cuando el login es exitoso
    LaunchedEffect(uiState.isSuccess)
    {
        if (uiState.isSuccess) {
            onLoginSuccess()
            viewModel.resetSuccess()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Box(
            Modifier
                .height(96.dp)
                .aspectRatio(1f)
                .background(Color.Magenta),
            contentAlignment = Alignment.Center,
        ) { Icon(imageVector = Icons.Filled.Add, contentDescription = null) }

        Header(Modifier.fillMaxWidth(),
            {
                Text("Clínica Vida",
                    Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold)
            },
            {
                Text("Consulta tus citas médicas.",
                    Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center)
            }
        )

        OutlinedTextField(
            value = uiState.model.mail,
            onValueChange = { viewModel.onMailChange(it) },
            label = { Text("Correo") },
            placeholder = { Text("Ingrese su correo electrónico.") },
            singleLine = true,
            isError = uiState.errorMsg != null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        OutlinedTextField(
            value = uiState.model.passwd,
            onValueChange = { viewModel.onPassChange(it) },
            label = { Text("Contraseña") },
            singleLine = true,
            isError = uiState.errorMsg != null,
            visualTransformation =
                if (uiState.showPassword) VisualTransformation.None
                else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(onDone = { viewModel.attemptLogin() }),
            trailingIcon = {
                IconButton(onClick = { viewModel.toggleShowPassword() }) {
                    Icon(
                        imageVector = if (uiState.showPassword) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff,
                        contentDescription = if (uiState.showPassword) "Ocultar contraseña"
                        else "Mostrar contraseña",
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
        )

        uiState.errorMsg?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Row(verticalAlignment = Alignment.CenterVertically,)
        {
            Checkbox(
                checked = uiState.rememberMe,
                onCheckedChange = { viewModel.toggleRememberMe(it) }
            )
            Text("Recordarme")
        }

        Button(
            onClick = { viewModel.attemptLogin() },
            enabled = uiState.model.mail.isNotBlank() && uiState.model.passwd.isNotBlank(),
            modifier = Modifier.fillMaxWidth(),
        ) { Text("Entrar") }
    }
}

@Composable
fun InicioScreen(modifier: Modifier = Modifier) {
    var logged by rememberSaveable { mutableStateOf(false) }

    if (!logged) {
        Login(modifier) { logged = true }
    } else {
        Column(
            modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Le damos la bienvenida.")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { logged = false }) {
                Text("Volver")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginPreview() {
    MaterialTheme {
        Surface { Login(onLoginSuccess = {}) }
    }
}