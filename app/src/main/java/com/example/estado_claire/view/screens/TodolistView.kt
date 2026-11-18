package com.example.estado_claire.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.estado_claire.model.Priority
import com.example.estado_claire.model.State
import com.example.estado_claire.view.components.Header
import com.example.estado_claire.view.components.TaskTag
import com.example.estado_claire.viewmodel.TodolistViewModel

@Composable
fun TodolistView(
    modifier: Modifier = Modifier,
    viewModel: TodolistViewModel = viewModel()
)
{
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier.padding(16.dp))
    {
        Header(Modifier.fillMaxWidth(), { Text("Mis pendientes") })

        Row(
            Modifier.fillMaxWidth().height(64.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            OutlinedTextField(
                value = state.newTaskText,
                onValueChange = viewModel::onNewTaskTextChange,
                modifier = Modifier.weight(1f).fillMaxHeight(),
                placeholder = { Text("Nueva tarea...") },
                singleLine = true
            )
            Button(
                onClick = viewModel::addTask,
                modifier = Modifier.fillMaxHeight().aspectRatio(1f),
                enabled = state.newTaskText.isNotBlank()
            ) { Text("+", fontSize = 32.sp) }
        }

        Row(
            Modifier.fillMaxWidth().padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            Text("Prioridad")
            Priority.entries.forEach()
            { priority ->
                FilterChip(
                    selected = state.selectedPriority == priority,
                    onClick = { viewModel.onPriorityChange(priority) },
                    label = { Text(priority.nom) }
                )
            }
        }

        Row(Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            State.entries.forEach()
            { filterState ->
                FilterChip(
                    selected = state.displayLevel == filterState,
                    onClick = { viewModel.onFilterChange(filterState) },
                    label = { Text(filterState.nom) }
                )
            }
        }

        Column(Modifier.weight(1f))
        {
            state.visibleTasks.forEach()
            { task ->
                TaskTag(
                    task = task,
                    onToggle = { viewModel.toggleTask(task) },
                    onDelete = { viewModel.deleteTask(task) }
                )
            }
        }

        Text(
            if (state.tasks.isNotEmpty())
                "Se han completado ${state.completedCount} tareas de ${state.tasks.size}"
            else
                "No hay ninguna tarea disponible",
            modifier = Modifier.padding(vertical = 8.dp)
        )

        OutlinedButton(
            onClick = viewModel::clearCompleted,
            enabled = state.completedCount > 0,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Borrar Completadas") }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewTodolist()
{
    MaterialTheme()
    { Surface { TodolistView() } }
}