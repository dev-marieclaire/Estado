package com.example.estado_claire.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.estado_claire.model.Taller
import com.example.estado_claire.view.components.Header
import com.example.estado_claire.viewmodel.TallerViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormTallerView(
    modifier: Modifier = Modifier,
    viewModel: TallerViewModel = viewModel()
)
{
    val state by viewModel.tallerState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(state.isSuccess)
    {
        if (state.isSuccess)
        {
            snackbarHostState.showSnackbar("¡Inscripción exitosa a ${state.taller.label}!")
            viewModel.resetSuccessState()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    )
    { paddingValues ->
        Column(modifier = modifier.padding(paddingValues).padding(12.dp)) {
            Header(
                Modifier.fillMaxWidth(),
                { Text("Inscripción a talleres", fontSize = 32.sp) },
                { Text("Casa del Lago · cupo limitado") }
            )

            OutlinedTextField(
                value = state.nombre,
                onValueChange = viewModel::onNombreChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Nombre completo") },
                isError = state.nombreError != null,
                supportingText = state.nombreError?.let { { Text(it) } },
                singleLine = true
            )

            OutlinedTextField(
                value = state.email,
                onValueChange = viewModel::onEmailChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Correo electrónico") },
                isError = state.emailError != null,
                supportingText = state.emailError?.let { { Text(it) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )

            OutlinedTextField(
                value = state.edad,
                onValueChange = viewModel::onEdadChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Edad") },
                isError = state.edadError != null,
                supportingText = state.edadError?.let { { Text(it) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth(),
            )
            {
                OutlinedTextField(
                    value = state.taller.label,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Taller") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    isError = state.tallerError != null,
                    supportingText = state.tallerError?.let { { Text(it) } },
                    modifier = Modifier
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth(),
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                )
                {
                    Taller.entries.filter { it != Taller.Ninguno }.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item.label) },
                            leadingIcon = if (state.taller == item) {
                                { Icon(Icons.Filled.Check, contentDescription = null) }
                            } else null,
                            onClick = {
                                viewModel.onTallerChange(item)
                                expanded = false
                            },
                        )
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = viewModel::validateForm,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Inscribirme") }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTallerView() {
    MaterialTheme {
        Surface { FormTallerView(Modifier.fillMaxSize()) }
    }
}