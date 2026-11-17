package com.example.estado_claire.model

enum class Spacing(val label: String, val multiplier: Float) {
    Compact("Compacto", 1.0f),
    Normal("Normal", 1.3f),
    Huge("Amplio", 1.6f)
}

enum class FontType(val isSerif: Boolean) {
    SANS(false),
    SERIF(true)
}

data class TextSettingsModel(
    val fontSize: Float = 14f,
    val fontType: FontType = FontType.SANS,
    val spacing: Spacing = Spacing.Normal,
    val obscureMode: Boolean = false
) {
    companion object
    {
        const val MIN_FONT_SIZE = 14f
        const val MAX_FONT_SIZE = 32f
    }

    fun changeFontSize(size: Float): TextSettingsModel =
        copy(fontSize = size.coerceIn(MIN_FONT_SIZE, MAX_FONT_SIZE))

    fun changeFontType(type: FontType): TextSettingsModel = copy(fontType = type)

    fun changeSpacing(size: Spacing): TextSettingsModel = copy(spacing = size)

    fun toggleObscureMode(): TextSettingsModel = copy(obscureMode = !obscureMode)
}