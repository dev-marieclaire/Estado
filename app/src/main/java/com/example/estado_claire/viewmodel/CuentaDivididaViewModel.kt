package com.example.estado_claire.viewmodel

import androidx.lifecycle.ViewModel
import com.example.estado_claire.model.CuentaDivididaModel
import com.example.estado_claire.model.Propina
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CuentaDivididaViewModel : ViewModel() {
    private val _cuenta = MutableStateFlow(CuentaDivididaModel())
    val cuenta: StateFlow<CuentaDivididaModel> = _cuenta.asStateFlow()

    fun setMonto(monto: Float) = _cuenta.update { it.setMonto(monto) }
    fun setPropina(propina: Propina) = _cuenta.update { it.setPropina(propina) }
    fun setCustomPropina(propina: Float) = _cuenta.update { it.setCustomPropina(propina) }
    fun addPersona() = _cuenta.update { it.addPersona() }
    fun removePersona() = _cuenta.update { it.removePersona() }
    fun setRedondeo(round: Boolean) = _cuenta.update { it.setRedondeo(round) }
}
