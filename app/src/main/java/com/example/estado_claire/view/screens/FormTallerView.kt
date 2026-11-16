package com.example.estado_claire.view.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.estado_claire.model.Taller
import com.example.estado_claire.view.components.Header
import com.example.estado_claire.viewmodel.TallerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormTallerView(
    modifier: Modifier = Modifier,
    viewModel: TallerViewModel = viewModel()
)
{
    val taller by viewModel.taller.collectAsStateWithLifecycle()

    Column(modifier.padding(12.dp))
    {
        Header(
            Modifier.fillMaxWidth(),
            { Text("Inscripción a talleres", fontSize = 32.sp) },
            { Text("Casa del Lago · cupo limitado") }
        )

        OutlinedTextField(
            taller.nomTextField,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Nombre completo") },
            isError = taller.nomTextFieldError != null,
            supportingText = {
                Text(
                    if (taller.nomTextFieldError != null) taller.nomTextFieldError.toString()
                    else ""
                )
            },
            lineLimits = TextFieldLineLimits.SingleLine
        )

        OutlinedTextField(
            taller.mailTextField,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Correo electrónico") },
            isError = taller.mailTextFieldError != null,
            supportingText = {
                Text(
                    if (taller.mailTextFieldError != null) taller.mailTextFieldError.toString()
                    else ""
                )
            },
            lineLimits = TextFieldLineLimits.SingleLine
        )

        OutlinedTextField(
            taller.ageTextField,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Edad") },
            isError = taller.ageTextFieldError != null,
            supportingText = {
                Text(
                    if (taller.ageTextFieldError != null) taller.ageTextFieldError.toString()
                    else ""
                )
            },
            lineLimits = TextFieldLineLimits.SingleLine
        )

        var expanded by rememberSaveable { mutableStateOf(false) }

        val labelTaller = { t: Taller ->
            when (t) {
                Taller.Ninguno -> "Ninguno"
                Taller.Fotografia -> "Fotografía"
                Taller.Musica -> "Música"
            }
        }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth(),
        ) {
            OutlinedTextField(
                value = labelTaller(taller.tallerDropdownField),
                onValueChange = {},
                readOnly = true,
                label = { Text("Taller") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                isError = taller.tallerDropdownFieldError != null,
                supportingText = {
                    taller.tallerDropdownFieldError?.let { Text(it) }
                },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth(),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            )
            {
                Taller.entries.filter { it != Taller.Ninguno }
                .forEach()
                { item ->
                    DropdownMenuItem(
                        text = { Text(labelTaller(item)) },
                        leadingIcon = if (taller.tallerDropdownField == item) {
                            { Icon(Icons.Filled.Check, contentDescription = null) }
                        } else null,
                        onClick = {
                            viewModel.changeTaller(item)
                            expanded = false
                        },
                    )
                }
            }
        }

        Spacer(Modifier.weight(1f))

        Button(onClick = { viewModel.validateForm() }, Modifier.fillMaxWidth())
        { Text("Inscribirme") }
    }
}

@Preview
@Composable
fun PreviewTallerView()
{
    Surface()
    { FormTallerView(Modifier.fillMaxSize()) }
}
