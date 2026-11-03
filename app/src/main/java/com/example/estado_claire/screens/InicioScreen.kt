package com.example.estado_claire

import android.graphics.fonts.Font
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.estado_claire.components.AmountAugmentation
import com.example.estado_claire.components.FilterChipSection
import com.example.estado_claire.components.Header
import com.example.estado_claire.components.LabeledSwitch
import com.example.estado_claire.components.SliderSection
import com.example.estado_claire.components.StatisticLine
import com.example.estado_claire.logic.Tip
import kotlin.plus

enum class Priority(val nom: String, val level: Int)
{
    Low("Baja", 0),
    Mid("Media", 1),
    Hi("Alta", 2)
}

enum class State(val nom: String)
{
    Any("Todas"),
    Pending("Pendiente"),
    Ready("Hecha")
}

data class Task(
    var name: String,
    var priotiry: Priority,
    var state: State
)

@Composable
fun TaskTag(
    modifier: Modifier = Modifier,
    task: Task,
    done: Boolean,
    onDoneChange: (Boolean) -> Unit,
    onDelete: () -> Unit
)
{
    Column(modifier.height(64.dp), verticalArrangement = Arrangement.spacedBy(8.dp))
    {
        Box(Modifier.fillMaxWidth().height(1.dp).background(Color.Gray))

        var isHighlighted by remember { mutableStateOf(false) }
        Row(
            Modifier.fillMaxWidth().weight(1f),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Checkbox(
                done,
                {
                    onDoneChange(it)
                    isHighlighted = it
                    if (it) task.state = State.Ready
                    else task.state = State.Pending
                }
            )

            Column(Modifier.weight(1f))
            {
                Text(
                    task.name,
                    fontSize = 18.sp,
                    textDecoration = if (isHighlighted) TextDecoration.LineThrough else TextDecoration.None,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    "Bajo",
                    Modifier.background(Color.Green).padding(4.dp)
                )
            }
            IconButton(onClick = onDelete)
            {
                Icon(
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = ""
                )
            }
        }
        Box(Modifier.fillMaxWidth().height(1.dp).background(Color.Gray))
    }
}

@Preview
@Composable
fun PreviewTaskTag()
{
    Surface()
    {
        var check by rememberSaveable { mutableStateOf(false) }
        TaskTag(
            Modifier.fillMaxWidth(),
            Task("Entregar el proyecto de OOP", Priority.Hi,  State.Pending),
            check,
            { check = it }
        )
        {}
    }
}

@Composable
fun InicioScreen(modifier: Modifier = Modifier)
{
    Column(modifier.padding(16.dp))
    {
        Header(Modifier.fillMaxWidth(), "Mis pendientes")

        var newTaskTextState = rememberTextFieldState()
        Row (
            Modifier.fillMaxWidth().height(64.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            OutlinedTextField(newTaskTextState, modifier = Modifier.weight(1f).fillMaxHeight())
            Button(
                onClick = {},
                modifier = Modifier.fillMaxHeight().aspectRatio(1f)
            ) { Text("+", fontSize = 32.sp) }
        }
        val newTaskText = newTaskTextState.text.toString()

        var selectedPriority by rememberSaveable { mutableStateOf(Priority.Mid) }
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            Text("Prioridad")
            Priority.entries.forEach()
            { priority ->
                FilterChip(
                    selected = selectedPriority == priority,
                    onClick = { selectedPriority = priority },
                    label = { Text(priority.nom) }
                )
            }
        }

        var displayLevel by rememberSaveable { mutableStateOf(State.Any) }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            State.entries.forEach()
            { state ->
                FilterChip(
                    selected = displayLevel == state,
                    onClick = { displayLevel = state },
                    label = { Text(state.nom) }
                )
            }
        }

        var check by rememberSaveable { mutableStateOf(false) }
        TaskTag(
            Modifier.fillMaxWidth(),
            Task("Entregar el proyecto de OOP", Priority.Hi,  State.Pending),
            check,
            { check = it }
        )
        {}
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
