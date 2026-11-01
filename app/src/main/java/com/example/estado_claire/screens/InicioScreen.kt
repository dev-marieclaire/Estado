package com.example.estado_claire

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.estado_claire.components.DropdownMenuSection
import com.example.estado_claire.components.Form
import com.example.estado_claire.components.Header
import com.example.estado_claire.logic.Taller
import com.example.estado_claire.logic.User
import com.example.estado_claire.logic.UserFormState


@Composable
fun InicioScreen(modifier: Modifier = Modifier)
{
    Column(modifier.padding(16.dp))
    {
        Header(Modifier.fillMaxWidth(),
            stringResource(R.string.talleres_header_label),
            stringResource(R.string.talleres_header_subtitle)
        )

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

@Preview
@Composable
fun PreviewInicioScreen()
{
    Surface {
        InicioScreen(Modifier.fillMaxSize())
    }
}
