package com.example.estado_claire.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.estado_claire.components.MiCard

@Composable
fun InicioScreen(modifier: Modifier = Modifier)
{
    var couleurs_list = rememberSaveable { mutableStateListOf<Color>() }
    var couleur_escrito by rememberSaveable { mutableStateOf<String>("") }

    var is_visible by remember { mutableStateOf(false) }

    Column(modifier = modifier
        .fillMaxSize()
        .background(Color.White))
    {
        Box(modifier =
            modifier
            .background(Color.White)
            .padding(15.dp))
        {
            Column(modifier = Modifier.fillMaxSize())
            {
                Spacer (modifier = Modifier.padding(10.dp))
                TextField(couleur_escrito, onValueChange = {couleur_escrito = it}, modifier = Modifier.fillMaxWidth(), singleLine = true)

                Spacer (modifier = Modifier.padding(10.dp))
                Button (onClick = {
                    if (couleur_escrito.isNotBlank() || couleur_escrito.isNotEmpty())
                    {
                        var couleur = Color.Unspecified

                        if (couleur_escrito.uppercase().equals("ROJO"))
                        {
                            couleur = Color.Red
                            is_visible = false
                        }
                        else if (couleur_escrito.uppercase().equals("AZUL"))
                        {
                            couleur = Color.Blue
                            is_visible = false
                        }
                        else if (couleur_escrito.uppercase().equals("AMARILLO"))
                        {
                            couleur = Color.Yellow
                            is_visible = false
                        }
                        else if (couleur_escrito.uppercase().equals("VERDE"))
                        {
                            couleur = Color.Green
                            is_visible = false
                        }
                        else if (couleur_escrito.uppercase().equals("BLANCO"))
                        {
                            couleur = Color.White
                            is_visible = false
                        }
                        else is_visible = true

                        couleurs_list.add(couleur)
                    }
                    else
                    {
                        is_visible = true
                    }
                    couleur_escrito = ""
                }, modifier = Modifier.fillMaxWidth())
                {
                    Text ("Agregar")
                }

                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.White))
                {
                    items(couleurs_list)
                    { nom ->
                        MiCard(modifier, nom)
                    }
                }

                AnimatedVisibility(visible = is_visible, modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .background(Color.Red))
                {
                    Box(modifier = Modifier.fillMaxSize())
                    {
                        Text("Color inválido.")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewInicioScreen()
{
    Surface()
    {
        InicioScreen(Modifier.fillMaxSize())
    }
}
