package com.example.estado_claire.viewmodel

import androidx.lifecycle.ViewModel
import com.example.estado_claire.model.Priority
import com.example.estado_claire.model.State
import com.example.estado_claire.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class TodolistUiState(
    val tasks: List<Task> = emptyList(),
    val newTaskText: String = "",
    val selectedPriority: Priority = Priority.Low,
    val displayLevel: State = State.Any
)
{
    val visibleTasks: List<Task>
        get() = when (displayLevel) {
            State.Any -> tasks
            else -> tasks.filter { it.state == displayLevel }
        }

    val completedCount: Int
        get() = tasks.count { it.state == State.Ready }
}

class TodolistViewModel : ViewModel()
{
    private val _uiState = MutableStateFlow(TodolistUiState())
    val uiState: StateFlow<TodolistUiState> = _uiState.asStateFlow()

    fun onNewTaskTextChange(text: String) = _uiState.update()
    { it.copy(newTaskText = text) }

    fun onPriorityChange(priority: Priority) = _uiState.update()
    { it.copy(selectedPriority = priority) }

    fun onFilterChange(state: State) = _uiState.update()
    { it.copy(displayLevel = state) }

    fun addTask()
    {
        val currentText = _uiState.value.newTaskText.trim()
        if (currentText.isNotEmpty())
        {
            val newTask = Task(name = currentText, priority = _uiState.value.selectedPriority)
            _uiState.update()
            {
                it.copy(
                    tasks = it.tasks + newTask,
                    newTaskText = "" // Limpiamos el campo de texto
                )
            }
        }
    }

    fun toggleTask(task: Task) = _uiState.update { state ->
        state.copy(
            tasks = state.tasks.map { if (it.id == task.id) it.toggleState() else it }
        )
    }

    fun deleteTask(task: Task) = _uiState.update()
    { state -> state.copy(tasks = state.tasks - task) }

    fun clearCompleted() = _uiState.update()
    { state -> state.copy(tasks = state.tasks.filterNot { it.state == State.Ready }) }
}
