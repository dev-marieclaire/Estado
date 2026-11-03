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
import androidx.compose.foundation.text.input.clearText
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
import androidx.compose.runtime.mutableStateListOf
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
import kotlin.collections.mutableListOf
import kotlin.plus

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.OutlinedButton

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

class Task(
    var name: String,
    var priotiry: Priority,
    initialState: State = State.Pending
) {
    var state by mutableStateOf(initialState)
}

@Composable
fun TaskTag(
    modifier: Modifier = Modifier,
    task: Task,
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
                task.state == State.Ready,
                {
                    task.state = if (it) State.Ready else State.Pending
                }
            )

            Column(Modifier.weight(1f))
            {
                Text(
                    task.name,
                    fontSize = 18.sp,
                    textDecoration = if (task.state == State.Ready) TextDecoration.LineThrough else TextDecoration.None,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    task.priotiry.nom,
                    Modifier.background(
                        if (task.priotiry == Priority.Low) Color(0x4400CC08)
                        else if (task.priotiry == Priority.Mid) Color(0x44FFFF00)
                        else Color(0x44FF1100)
                    ).padding(4.dp),
                    color = if (task.priotiry == Priority.Low) Color(0xFF008824)
                        else if (task.priotiry == Priority.Mid) Color(0xFFFFAA00)
                        else Color(0xFF893300)
                )
            }
            IconButton(onClick = onDelete)
            {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Eliminar"
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
            Task("Entregar el proyecto de OOP", Priority.Hi,  State.Pending)
        )
        {}
    }
}

@Composable
fun InicioScreen(modifier: Modifier = Modifier)
{
    var taskList = rememberSaveable { mutableStateListOf<Task>() }
    var newTaskTextState = rememberTextFieldState()
    val newTaskText = newTaskTextState.text.toString()
    var selectedPriority by rememberSaveable { mutableStateOf(Priority.Mid) }
    var displayLevel by rememberSaveable { mutableStateOf(State.Any) }
    val pendingCount   = taskList.count { it.state == State.Pending }
    val completedCount = taskList.count { it.state == State.Ready }

    var visibleTasks = when (displayLevel) // Cuando el nivel de visibilidad
    {
        State.Any -> taskList // Sea cualquiera, entonces visibleTasks = taskList
        else -> taskList.filter { it.state == displayLevel } // Si no, que agarre aquellos elementos que coincidan con el displayLevel
    }

    Column(modifier.padding(16.dp))
    {
        Header(Modifier.fillMaxWidth(), "Mis pendientes")

        Row (
            Modifier.fillMaxWidth().height(64.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            OutlinedTextField(newTaskTextState, modifier = Modifier.weight(1f).fillMaxHeight())
            Button(
                onClick = {
                    taskList.add(
                        Task(newTaskText, selectedPriority)
                    )

                    newTaskTextState.clearText()
                },
                modifier = Modifier.fillMaxHeight().aspectRatio(1f)
            ) { Text("+", fontSize = 32.sp) }
        }

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

        Column (Modifier.weight(1f))
        {
            visibleTasks.forEach()
            { task ->
                TaskTag(
                    Modifier.fillMaxWidth(),
                    task,
                ) { taskList.remove(task) } // Como visibleTasks son apuntadores a taskList, los elementos son practicamente los mismos para la computadora.
            }
        }

        Text(
            if (taskList.size > 0) "Se han completado ${completedCount} tareas de ${taskList.size}"
            else "No hay ninguna tarea disponible"
        )

        OutlinedButton(
            onClick = { taskList.removeAll { it.state == State.Ready } },
            enabled = if (completedCount > 0) true
            else false
        )
        { Text("Borrar Completadas") }
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
