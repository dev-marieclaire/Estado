package com.example.estado_claire.viewmodel

import androidx.lifecycle.ViewModel
import com.example.estado_claire.model.FontType
import com.example.estado_claire.model.Spacing
import com.example.estado_claire.model.TextSettingsModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TextSettingsViewModel : ViewModel()
{
    var _settings = MutableStateFlow(TextSettingsModel())
        private set

    val settings: StateFlow<TextSettingsModel> = _settings.asStateFlow()

    fun changeFontSize(size: Float)
    { _settings.value = _settings.value.changeFontSize(size) }

    fun changeFontType(type: FontType)
    { _settings.value = _settings.value.changeFontType(type) }

    fun changeSpacing(size: Spacing)
    { _settings.value = _settings.value.changeSpacing(size) }

    fun toggleObscureMode()
    { _settings.value = _settings.value.toggleObscureMode() }
}
