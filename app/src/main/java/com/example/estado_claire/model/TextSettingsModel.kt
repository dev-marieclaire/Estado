package com.example.estado_claire.model

import androidx.compose.ui.text.font.Font

enum class Spacing(val label: String, val multiplier: Float)
{
    Compact("Compacto", 1.0f),
    Normal("Normal", 1.3f),
    Huge("Amplio", 1.6f)
}

enum class FontType(val type: Boolean)
{
    SANS(false),
    SERIF(true)
}

data class TextSettingsModel(
    val fontSize: Float = 14f,
    val fontType: FontType = FontType.SANS,
    val spacing: Spacing = Spacing.Normal,
    val obscureMode: Boolean = false
)
{
    val MIN_FONT_SIZE: Float = 14f
    val MAX_FONT_SIZE: Float = 32f

    fun changeFontSize(size: Float): TextSettingsModel =
        if (size in MIN_FONT_SIZE .. MAX_FONT_SIZE) copy(fontSize = size)
        else this

    fun changeFontType(type: FontType): TextSettingsModel = copy(fontType = type)

    fun changeSpacing(size: Spacing): TextSettingsModel = copy(spacing = size)

    fun toggleObscureMode(): TextSettingsModel = copy(obscureMode = !obscureMode)
}