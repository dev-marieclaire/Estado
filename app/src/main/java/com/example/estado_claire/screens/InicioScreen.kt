package com.example.estado_claire

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.estado_claire.components.Header
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

val StarShape = GenericShape { size, _ ->
    val cx = size.width / 2f
    val cy = size.height / 2f
    val outerR = minOf(cx, cy)
    val innerR = outerR * 0.4f
    var angle = -PI / 2

    moveTo(cx + outerR * cos(angle).toFloat(), cy + outerR * sin(angle).toFloat())

    for (i in 1..10) {
        angle += PI / 5
        val r = if (i % 2 == 0) outerR else innerR
        lineTo(cx + r * cos(angle).toFloat(), cy + r * sin(angle).toFloat())
    }
    close()
}

enum class RatingLevel(
    val text: String,
    val score: Int
)
{
    Ninguno("", 0),
    Malo("Malo", 1),
    Regular("Regular", 2),
    Bueno("Bueno", 3),
    Buenisimo("Muy bueno", 4),
    Excelete("Excelente", 5)
}

data class RatingCategory(
    val nom: String,
    var rating: MutableState<RatingLevel>
)

@SuppressLint("DefaultLocale")
@Composable
fun ReviewScreen(
    modifier: Modifier = Modifier,
    completed: Boolean,
    onCompleted: (Boolean) -> Unit
)
{
    val selectedRatingLevelList = remember(completed) {
        mutableStateListOf<RatingCategory>(
            RatingCategory("Comida", mutableStateOf(RatingLevel.Ninguno)),
            RatingCategory("Servicio", mutableStateOf(RatingLevel.Ninguno)),
            RatingCategory("Ambiente", mutableStateOf(RatingLevel.Ninguno))
        )
    }

    Column(
        modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp))
    {
        Header(modifier = Modifier.fillMaxWidth(), "Escribe tu reseña")

        Text("Restaurante el Mezquite")
        Text("Califica tu experiencia")

        Column()
        {
            selectedRatingLevelList.forEach()
            { selection ->
                Text(selection.nom)

                Row(Modifier.fillMaxWidth())
                {
                    Row(Modifier.weight(1f))
                    {
                        RatingLevel.entries.forEachIndexed()
                        { index, item ->
                            if (index > 0)
                            {
                                Button(
                                    onClick = { selection.rating.value = item },
                                    modifier = Modifier.size(32.dp),
                                    shape = StarShape,
                                    contentPadding = PaddingValues(1.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Gray,
                                        contentColor = if (index <= selection.rating.value.score) Color.Yellow else Color.Gray,
                                    )
                                ) { Icon(Icons.Filled.Star, contentDescription = null) }
                            }
                        }
                    }

                    Text(selection.rating.value.text)
                }
            }

            val sumatoria = selectedRatingLevelList.fold(0)
            { sum, item -> sum + item.rating.value.score }

            val promedio: Double = sumatoria.toDouble() / selectedRatingLevelList.size

            Row(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                    .background(Color(0x11333333))
                    .padding(all = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text("Calificación General")
                Row()
                {
                    Text(String.format("%.2f", promedio))
                    Icon(Icons.Filled.Star, contentDescription = null)
                }
            }

            val state = rememberTextFieldState()

            val texto       = state.text.toString()
            val longitud    = texto.length
            val tieneTrailing = texto != texto.trimEnd()
            val esSoloEspacios = texto.isBlank()

            val error = when {
                longitud in 1..44 -> "El comentario debe contener al menos 45 caracteres."
                longitud > 200 -> "El comentario no puede tener más de 200 caracteres."
                esSoloEspacios && longitud > 0 -> "El comentario no puede ser solo espacios."
                tieneTrailing -> "Evita los espacios al final del comentario."
                else -> null
            }

            Column(Modifier.weight(1f))
            {
                OutlinedTextField(
                    state,
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    label = { Text("Comentario") },
                    isError = error != null,
                    supportingText = {
                        Row(Modifier.fillMaxWidth()) {
                            error?.let { Text(it) }
                            Spacer(Modifier.weight(1f))
                            Text("${state.text.toString().length}/${200}")
                        }
                    },
                )
            }

            val countNingunos = selectedRatingLevelList.count { it.rating.value == RatingLevel.Ninguno }

            Button({ onCompleted(true) },
                Modifier.fillMaxWidth(),
                enabled = countNingunos == 0
                        && !esSoloEspacios
                        && longitud in 45..200
                        && error == null
            )
            { Text("Publicar") }
        }
    }
}

@Composable
fun InicioScreen(modifier: Modifier = Modifier)
{
    var completed by remember { mutableStateOf(false) }

    if (completed == false)
    {
        ReviewScreen(
            modifier,
            completed,
            { completed = it }
        )
    }
    else
    {
        Column(modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text("¡Gracias por compartir tu opinión!")

            Button({ completed = !completed })
            { Text("Volver") }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InicioPreview() {
    MaterialTheme { Surface(Modifier.fillMaxSize()) { InicioScreen() } }
}
