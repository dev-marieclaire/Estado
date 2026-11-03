package com.example.estado_claire

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.estado_claire.components.DropdownMenuSection
import com.example.estado_claire.components.Form
import com.example.estado_claire.components.Header
import com.example.estado_claire.logic.Pantalla
import com.example.estado_claire.logic.Taller
import com.example.estado_claire.logic.User
import com.example.estado_claire.logic.UserFormState

@Composable
fun UserForm(
    modifier: Modifier = Modifier,
    onCurrentScreenChange: (Pantalla) -> Unit,
    onUserChange: (User) -> Unit
)
{
    Column(modifier.padding(16.dp))
    {
        Header(Modifier.fillMaxWidth(),
            stringResource(R.string.talleres_header_label),
            stringResource(R.string.talleres_header_subtitle)
        )

        val formState = remember { UserFormState() }
//        var userList = remember { mutableStateListOf<User>() }
        var userCounterTallerFotografia by rememberSaveable { mutableStateOf(0) }
        var userCounterTallerMusica by rememberSaveable { mutableStateOf(0) }

        var errorTextForm by remember { mutableStateOf("") }

        Form(
            modifier = Modifier.fillMaxWidth(),
            formState = formState,
            onSubmit = { validUser, taller, isValid ->
                if (isValid)
                {
                    val cupo = taller.cupo
                    val hayCupo = when (taller)
                    {
                        Taller.FOTOGRAFIA -> userCounterTallerFotografia < cupo
                        Taller.MUSICA     -> userCounterTallerMusica     < cupo
                        else -> null
                    }

                    if (hayCupo == true)
                    {
                        when (taller) {
                            Taller.FOTOGRAFIA -> userCounterTallerFotografia += 1
                            Taller.MUSICA     -> userCounterTallerMusica     += 1
                            else -> null
                        }
                        errorTextForm = ""
                    }
                    else
                    {
                        errorTextForm = "No se pudo inscribir al taller de ${taller.label}: cupo lleno"
                    }

                    onCurrentScreenChange(Pantalla.FormularioLleno)
                    onUserChange(validUser)
                }
            }
        )

        if (errorTextForm != "")
            Text(errorTextForm, modifier = Modifier.fillMaxWidth().background(Color(0x33FF2200)).padding(8.dp))
    }
}

@Composable
fun InicioScreen(modifier: Modifier = Modifier)
{
    var currentScreen by remember { mutableStateOf(Pantalla.Formulario) }
    var user by remember { mutableStateOf(User()) }
    if (currentScreen == Pantalla.Formulario)
    {
        UserForm(
            modifier,
            { currentScreen = it},
            onUserChange = { user = it}
        )
    }
    else
    {
        Column(modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Text("Su solicitud ha sido completada!", fontSize = 24.sp, modifier = Modifier.padding(bottom = 8.dp))
            Text("Nombre: ${user.nom}", fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))
            Text("Correo: ${user.mail}", fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))
            Text("Edad: ${user.edad}", fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))
            Text("Taller: ${user.taller.label}", fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))

            Button(onClick = { currentScreen = Pantalla.Formulario } )
            { Text("Volver") }
        }
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
