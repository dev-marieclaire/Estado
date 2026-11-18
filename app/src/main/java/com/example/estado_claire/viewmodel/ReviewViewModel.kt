package com.example.estado_claire.viewmodel

import androidx.lifecycle.ViewModel
import com.example.estado_claire.model.RatingCategory
import com.example.estado_claire.model.RatingLevel
import com.example.estado_claire.model.ReviewConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ReviewUiState(
    val categories: List<RatingCategory> = listOf(
        RatingCategory("Comida"),
        RatingCategory("Servicio"),
        RatingCategory("Ambiente")
    ),
    val comment: String = "",
    val isSuccess: Boolean = false
)
{
    val averageRating: Double
        get() = categories.map { it.rating.score }.average().takeIf { !it.isNaN() } ?: 0.0

    val commentError: String?
        get() {
            val length = comment.length
            return when {
                length in 1..44 -> "El comentario debe contener al menos 45 caracteres."
                length > 200 -> "El comentario no puede tener más de 200 caracteres."
                comment.isBlank() && length > 0 -> "El comentario no puede ser solo espacios."
                comment != comment.trimEnd() -> "Evita los espacios al final del comentario."
                else -> null
            }
        }

    val canSubmit: Boolean
        get() = categories.none { it.rating == RatingLevel.Ninguno } &&
                comment.isNotBlank() &&
                comment.length in ReviewConstants.MIN_COMMENT_LENGTH..ReviewConstants.MAX_COMMENT_LENGTH &&
                commentError == null
}

class ReviewViewModel : ViewModel()
{
    private val _uiState = MutableStateFlow(ReviewUiState())
    val uiState: StateFlow<ReviewUiState> = _uiState.asStateFlow()

    fun onRatingChange(categoryName: String, newRating: RatingLevel) = _uiState.update()
    { state ->
        state.copy(
            categories = state.categories.map()
            { if (it.nom == categoryName) it.copy(rating = newRating) else it }
        )
    }

    fun onCommentChange(text: String) = _uiState.update()
    { it.copy(comment = text) }

    fun submitReview()
    {
        if (_uiState.value.canSubmit)
        { _uiState.update { it.copy(isSuccess = true) } }
    }

    fun resetReview()
    { _uiState.update { ReviewUiState() } }
}