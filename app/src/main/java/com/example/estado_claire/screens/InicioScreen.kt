package com.example.estado_claire

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Label
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.estado_claire.components.Header

enum class GuessState()
{
    None,
    True,
    False
}

@Composable
fun Card(
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    label: @Composable () -> Unit,
    state: GuessState
)
{
    var cardColor = when {
        state == GuessState.True -> Color(0x3300FF33)
        state == GuessState.False -> Color(0x33FF0000)
        else -> Color.Unspecified
    }

    var borderColor = when {
        state == GuessState.True -> Color(0x8800CC55)
        state == GuessState.False -> Color(0x88FF0033)
        else -> Color.Gray
    }
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(cardColor)
            .border(1.5.dp, borderColor, RoundedCornerShape(16.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        RadioButton(
            selected,
            onClick = { onSelectedChange(true) },
            enabled = state == GuessState.None
        )

        label()

        if (state != GuessState.None)
        {
            Icon(
                imageVector =
                    if (state == GuessState.True) Icons.Outlined.Check
                    else Icons.Outlined.Close,
                null
            )
        }
    }
}

class Question(
    val question: String,
    val responses: List<String>,
    val answer: Int
)
{
    init
    {
        require(responses.size >= 4)
        { "Se requieren al menos cuatro reactivos." }
    }

    fun check(submission: Int): Boolean
    {
        if (submission == answer) return true
        return false
    }
}

@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    onCompleted: (Int) -> Unit
)
{
    var quizQuestionList = listOf<Question>(
        Question(
            "¿Qué tipo de dato conviene usar para guardar 3.14?",
            listOf("Int", "Double", "String", "Boolean"),
            1
        ),
        Question(
            "¿Qué imprime listOf(1, 2, 3, 4).sumOf { it * 2 }?",
            listOf("10", "20", "[2, 4, 6, 8]", "Error de compilación"),
            1
        ),
        Question(
            "¿Cuál es la forma correcta de convertir un String a número sin que truene si el texto no es válido?",
            listOf("\"abc\".toInt()", "\"abc\".toIntOrNull() ?: 0", "Integer.parseInt(\"abc\")", "\"abc\".asInt()"),
            1
        ),
        Question(
            "¿Qué devuelve este código?\n\nval gastos = listOf(\"Comida\" to 120.0, \"Transporte\" to 80.0, \"Comida\" to 50.0)\ngastos.groupBy { it.first }.mapValues { (_, v) -> v.sumOf { it.second } }[\"Comida\"]",
            listOf("120.0", "170.0", "250.0", "null"),
            1
        ),
        Question(
            "En un when sobre una sealed class sin rama else, ¿qué pasa si mañana se añade un nuevo subtipo?",
            listOf(
                "Compila y devuelve null para el nuevo caso",
                "Lanza una excepción en tiempo de ejecución",
                "No compila hasta que se cubra el nuevo caso",
                "Se ignora silenciosamente"
            ),
            2
        )
    )

    var counter by remember { mutableStateOf(0) }
    var guessed by remember { mutableStateOf(0) }
    var selectedAnswer by remember(counter) { mutableStateOf<Int?>(null) }
    var canShowResults by remember(counter) { mutableStateOf(false) }

    val currentQuestion = quizQuestionList[counter]
    val progress = (counter + if (canShowResults) 1 else 0).toFloat() / quizQuestionList.size

    Column(
        modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp))
    {
        Header(Modifier.fillMaxWidth(), "Quiz de programación")

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
        {
            Text("Pregunta ${counter + 1} de 5")
            Text("Puntaje: ${guessed}")
        }

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth(),
            color = Color.Green
        )

        val index: Int = counter
        val currentQuestion = quizQuestionList.get(index)
        val currentListOfResponses = currentQuestion.responses

        var isCorrect by remember { mutableStateOf(false) }

        Text(currentQuestion.question)

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp))
        {
            items(currentListOfResponses)
            { item ->
                val isCorrectIndex = currentListOfResponses.indexOf(item) == currentQuestion.answer
                val isChosenIndex = currentListOfResponses.indexOf(item) == selectedAnswer

                isCorrect = selectedAnswer == currentQuestion.answer

                val state = when {
                    !canShowResults -> GuessState.None
                    isCorrectIndex == true -> GuessState.True
                    isChosenIndex == true && !isCorrectIndex -> GuessState.False
                    else -> GuessState.None
                }

                Card(
                    currentListOfResponses.indexOf(item) == selectedAnswer,
                    onSelectedChange = {
                        if (canShowResults == false)
                            selectedAnswer = currentListOfResponses.indexOf(item)
                    },
                    label = { Text(item, modifier = Modifier.weight(1f)) },
                    state = state
                )
            }
        }

        if (canShowResults)
        {
            if (isCorrect)
            {
                Text(
                    "Correcto.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0x3300FF33))
                        .padding(8.dp)
                )
            }
            else
            {
                Text(
                    "Incorrecto. La respuesta correcta es ${
                        currentListOfResponses.get(
                            currentQuestion.answer
                        )
                    }",
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0x33FF3322))
                        .padding(8.dp)
                )
            }
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp))
        {
            Button(
                {
                    canShowResults = true

                    if (isCorrect) guessed += 1
                },
                enabled = !canShowResults && selectedAnswer != null
            ) { Text("Confirmar") }

            Button({
                if (selectedAnswer != null)
                {
                    if (counter < quizQuestionList.lastIndex) {
                        counter += 1
                    } else {
                        onCompleted(guessed)
                    }
                }
            },
                modifier = Modifier.weight(1f),
                enabled = canShowResults != false
            ) { Text(if (counter < quizQuestionList.lastIndex) "Siguiente" else "Terminar") }
        }
    }
}

@Composable
fun InicioScreen(modifier: Modifier = Modifier)
{
    var result by rememberSaveable() { mutableStateOf<Int?>(null) }

    if (result == null)
    { QuizScreen(modifier) { result = it } }
    else
    {
        Column(modifier)
        { Text("Muy bien, nom d'utilisatrice") }
    }
}

@Preview(showBackground = true)
@Composable
private fun InicioPreview() {
    MaterialTheme { Surface(Modifier.fillMaxSize()) { InicioScreen() } }
}
