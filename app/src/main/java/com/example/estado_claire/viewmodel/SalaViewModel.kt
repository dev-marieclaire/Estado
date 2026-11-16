package com.example.estado_claire.viewmodel

import androidx.lifecycle.ViewModel
import com.example.estado_claire.model.SalaModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SalaViewModel : ViewModel()
{
    var _sala = MutableStateFlow(SalaModel())
        private set

    val sala: StateFlow<SalaModel> = _sala.asStateFlow()

    fun incrementar()
    { _sala.value = _sala.value.incrementar() }

    fun decrementar()
    { _sala.value = _sala.value.decrementar() }

    fun restablecer()
    { _sala.value = _sala.value.restablecer() }
}
