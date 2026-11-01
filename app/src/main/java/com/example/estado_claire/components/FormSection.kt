package com.example.estado_claire.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.estado_claire.logic.Taller
import com.example.estado_claire.logic.User
import com.example.estado_claire.logic.UserFormState

@Composable
fun Form(
    formState: UserFormState,
    modifier: Modifier = Modifier,
    onSubmit: (User) -> Unit
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Campo Nombre
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = formState.user.nom,
            onValueChange = { formState.updateNom(it) },
            label = { Text("Nombre Completo") },
            isError = formState.nomError != null,
            supportingText = {
                formState.nomError?.let { Text(it) }
            }
        )

        // Campo Correo
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = formState.user.mail,
            onValueChange = { formState.updateMail(it) },
            label = { Text("Correo Electrónico") },
            isError = formState.mailError != null,
            supportingText = {
                formState.mailError?.let { Text(it) }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        // Campo Edad
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = formState.edadText,
            onValueChange = {
                if (it.isDigitsOnly()) formState.updateEdad(it)
                else if (it.isBlank()) formState.updateEdad("")
            },
            label = { Text("Edad") },
            isError = formState.edadError != null,
            supportingText = {
                formState.edadError?.let { Text(it) }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        DropdownMenuSection(
            label = "Taller",
            selectedOption = formState.user.taller,
            onSelectedOptionChange = { formState.updateTaller(it) },
            isError = formState.tallerError != null,
            supportingText = {
                formState.tallerError?.let { Text(it) }
            }
        )

        // Botón de envío
        Button(
            onClick = {
                if (formState.validate()) {
                    onSubmit(formState.user)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Inscribirme")
        }
    }
}

@Preview
@Composable
fun PreviewFromSection()
{
    Surface()
    {
        val formState = remember { UserFormState() }
        Form(
            modifier = Modifier.fillMaxWidth(),
            formState = formState,
            onSubmit = {
                validUser ->
                println("Usuario listo para dar de alta: $validUser")
            }
        )
    }
}