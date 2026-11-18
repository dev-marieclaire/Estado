package com.example.estado_claire.model

import java.util.UUID

enum class Priority(val nom: String, val level: Int) {
    Low("Baja", 0),
    Mid("Media", 1),
    Hi("Alta", 2)
}

enum class State(val nom: String) {
    Any("Todas"),
    Pending("Pendiente"),
    Ready("Hecha")
}

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val priority: Priority,
    val state: State = State.Pending
)
{
    fun toggleState(): Task = copy(state = if (state == State.Pending) State.Ready else State.Pending)
}