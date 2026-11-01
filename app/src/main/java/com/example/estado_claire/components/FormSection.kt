package com.example.estado_claire.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.estado_claire.logic.User

@Composable
fun Form(
    modifier: Modifier = Modifier,
    user: User,
    onUserChange: (User) -> Unit
)
{
    Column(modifier)
    {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = user.nom,
            onValueChange = { onUserChange(user.copy(nom = it)) },
            label = { Text("Nombre Completo") }
        )

        val mailError = user.mail.isNotBlank() && !user.mail.contains("@")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = user.mail,
            onValueChange = { onUserChange(user.copy(mail = it)) },
            label = { Text("Correo Electrónico") }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = user.edad.toString(),
            onValueChange = {
                text ->
                text.toIntOrNull()?.let { onUserChange(user.copy(edad = it)) }
            },
            label = { Text("Edad") }
        )
    }
}

@Preview
@Composable
fun PreviewForm()
{
    Surface()
    {
        var user by rememberSaveable { mutableStateOf(User()) }
        Form(Modifier.fillMaxWidth(), user, onUserChange = { user = it })
    }
}
