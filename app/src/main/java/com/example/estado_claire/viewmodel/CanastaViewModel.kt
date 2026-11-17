package com.example.estado_claire.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.estado_claire.model.CanastaModel
import com.example.estado_claire.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CanastaViewModel : ViewModel()
{
    private val _canasta = MutableStateFlow(CanastaModel())
    val canasta: StateFlow<CanastaModel> = _canasta.asStateFlow()

    fun addProducto(p: Producto) { _canasta.value = _canasta.value.agregar(p) }
    fun deleteProducto(p: Producto) { _canasta.value = _canasta.value.quitar(p) }

    fun vaciar() { _canasta.value = _canasta.value.vaciar() }
}
