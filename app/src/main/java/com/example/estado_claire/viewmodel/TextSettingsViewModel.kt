package com.example.estado_claire.viewmodel

import androidx.lifecycle.ViewModel
import com.example.estado_claire.model.FontType
import com.example.estado_claire.model.Spacing
import com.example.estado_claire.model.TextSettingsModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TextSettingsViewModel : ViewModel()
{
    private val _settings = MutableStateFlow(TextSettingsModel())
    val settings: StateFlow<TextSettingsModel> = _settings.asStateFlow()

    fun changeFontSize(size: Float) =
        _settings.update { it.changeFontSize(size) }

    fun changeFontType(type: FontType) =
        _settings.update { it.changeFontType(type) }

    fun changeSpacing(size: Spacing) =
        _settings.update { it.changeSpacing(size) }

    fun toggleObscureMode() =
        _settings.update { it.toggleObscureMode() }
}
