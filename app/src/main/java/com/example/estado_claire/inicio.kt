package com.example.estado_claire

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.estado_claire.components.MiCard

@Composable
//fun InicioScreen(modifier: Modifier, nombre: String, add:() -> Unit, update: () -> Unit, delete: () -> Unit)
fun InicioScreen(modifier: Modifier, nombre: String)
{
    var nom_list by rememberSaveable { mutableStateListOf<String>() };
    var nom_ecris by rememberSaveable { mutableStateOf("") }

    Column()
    {
        TextField(nom_ecris, onValueChange = nom_ecris, modifier = Modifier)
        Button({nom_list.add(nom_ecris)})
        {
            Text("Añadir", fontSize = 32.sp)
        }
    }

    LazyColumn()
    {
        items(nom_list)
        { nom ->
            MiCard(modifier = modifier.fillMaxWidth().height(64.dp), nom, Color.Cyan,
                {
                    nom_ecris = nom_list.get();
                    nom_list.remove(nom);
                },
                {nom_list.remove(nom);}
            )
        }
    }
}