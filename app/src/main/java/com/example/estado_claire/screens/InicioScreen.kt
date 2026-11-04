package com.example.estado_claire

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.estado_claire.components.Header

private const val DEMO_MAIL = "paciente@vida.mx"
private const val DEMO_PASS = "Vida2025"

private fun isValid(mail: String, passwd: String) =
    mail.trim() == DEMO_MAIL && passwd == DEMO_PASS

@Composable
fun Login(
    modifier: Modifier = Modifier,
    onSubmit: (Boolean) -> Unit,
)
{
    var mail         by rememberSaveable { mutableStateOf("") }
    var passwd       by rememberSaveable { mutableStateOf("") }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var souvient     by rememberSaveable { mutableStateOf(false) }
    var errorMsg     by rememberSaveable { mutableStateOf<String?>(null) }

    fun attemptLogin()
    {
        val ok = isValid(mail, passwd)
        errorMsg = if (ok) null else "Correo o contraseña incorrectos"
        onSubmit(ok)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    )
    {
        Box(
            Modifier
                .height(96.dp)
                .aspectRatio(1f)
                .background(Color.Magenta),
            contentAlignment = Alignment.Center,
        ) { Icon(Icons.Filled.Add, contentDescription = null) }

        Header(Modifier.fillMaxWidth(), "Clínica Vida", "Consulta tus citas médicas.")

        OutlinedTextField(
            value = mail,
            onValueChange = { mail = it; errorMsg = null },
            label = { Text("Correo") },
            placeholder = { Text("Ingrese su correo electrónico.") },
            singleLine = true,
            isError = errorMsg != null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        OutlinedTextField(
            value = passwd,
            onValueChange = { passwd = it; errorMsg = null },
            label = { Text("Contraseña") },
            singleLine = true,
            isError = errorMsg != null,
            visualTransformation =
                if (showPassword) VisualTransformation.None
                else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(onDone = { attemptLogin() }),
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword })
                {
                    Icon(
                        imageVector =
                            if (showPassword) Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff,
                        contentDescription =
                            if (showPassword) "Ocultar contraseña"
                            else "Mostrar contraseña",
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
        )

        errorMsg?.let { Text(it, color = MaterialTheme.colorScheme.error) }

        Row(verticalAlignment = Alignment.CenterVertically)
        {
            Checkbox(checked = souvient, onCheckedChange = { souvient = it })
            Text("Recordarme")
        }

        Button(
            onClick = { attemptLogin() },
            enabled = mail.isNotBlank() && passwd.isNotBlank(),
            modifier = Modifier.fillMaxWidth(),
        ) { Text("Entrar") }
    }
}

@Composable
fun InicioScreen(modifier: Modifier = Modifier) {
    var logged by rememberSaveable { mutableStateOf(false) }

    if (!logged) Login(modifier) { logged = it }
    else
    {
        Column(modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center)
        {
            Text("Le damos la bienvenida.")
            Button({ logged = false })
            {
                Text("Volver")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginPreview() {
    MaterialTheme { Surface { Login(onSubmit = {}) } }
}

@Preview(showBackground = true)
@Composable
private fun InicioPreview() {
    MaterialTheme { Surface(Modifier.fillMaxSize()) { InicioScreen() } }
}