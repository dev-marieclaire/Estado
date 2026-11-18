package com.example.estado_claire.view.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.estado_claire.model.RatingLevel
import com.example.estado_claire.model.ReviewConstants
import com.example.estado_claire.view.components.Header
import com.example.estado_claire.viewmodel.ReviewViewModel
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

val StarShape = GenericShape { size, _ ->
    val cx = size.width / 2f
    val cy = size.height / 2f
    val outerR = minOf(cx, cy)
    val innerR = outerR * 0.4f
    var angle = -PI / 2
    moveTo(cx + outerR * cos(angle).toFloat(), cy + outerR * sin(angle).toFloat())
    for (i in 1..10) {
        angle += PI / 5
        val r = if (i % 2 == 0) outerR else innerR
        lineTo(cx + r * cos(angle).toFloat(), cy + r * sin(angle).toFloat())
    }
    close()
}

@SuppressLint("DefaultLocale")
@Composable
fun ReviewAppScreen(
    modifier: Modifier = Modifier,
    viewModel: ReviewViewModel = viewModel()
)
{
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    if (state.isSuccess)
    {
        ThankYouScreen(
            modifier = modifier,
            onRestart = viewModel::resetReview
        )
    }
    else
    {
        ReviewScreen(
            modifier = modifier,
            state = state,
            onRatingChange = viewModel::onRatingChange,
            onCommentChange = viewModel::onCommentChange,
            onSubmit = viewModel::submitReview
        )
    }
}

@Composable
private fun ReviewScreen(
    modifier: Modifier = Modifier,
    state: com.example.estado_claire.viewmodel.ReviewUiState,
    onRatingChange: (String, RatingLevel) -> Unit,
    onCommentChange: (String) -> Unit,
    onSubmit: () -> Unit
)
{
    Column(
        modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        Header(modifier = Modifier.fillMaxWidth(), { Text("Escribe tu reseña") })

        Text("Restaurante el Mezquite", style = MaterialTheme.typography.titleMedium)
        Text("Califica tu experiencia")

        Column(verticalArrangement = Arrangement.spacedBy(16.dp))
        {
            state.categories.forEach()
            { category ->
                Text(category.nom)

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp))
                {
                    RatingLevel.entries.drop(1).forEach()
                    { level ->
                        val isSelected = category.rating.score >= level.score

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(StarShape)
                                .clickable { onRatingChange(category.nom, level) },
                            contentAlignment = Alignment.Center
                        )
                        {
                            Icon(
                                imageVector = if (isSelected) Icons.Filled.Star else Icons.Outlined.StarOutline,
                                contentDescription = level.text,
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(StarShape)
                                    .background(if (isSelected) Color.Yellow else Color.Transparent)
                                    .padding(2.dp),
                                tint = if (isSelected) Color(0xFFFFA000) else Color.Gray
                            )
                        }
                    }
                }
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                .background(Color(0x11333333))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Text("Calificación General")
            Row()
            {
                Text(String.format("%.2f", state.averageRating))
                Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFA000))
            }
        }

        OutlinedTextField(
            value = state.comment,
            onValueChange = onCommentChange,
            modifier = Modifier.fillMaxWidth().weight(1f),
            label = { Text("Comentario") },
            isError = state.commentError != null,
            supportingText = {
                Row(Modifier.fillMaxWidth()) {
                    state.commentError?.let { Text(it, modifier = Modifier.weight(1f)) }
                    Text("${state.comment.length}/${ReviewConstants.MAX_COMMENT_LENGTH}")
                }
            },
        )

        Button(
            onClick = onSubmit,
            modifier = Modifier.fillMaxWidth(),
            enabled = state.canSubmit
        ) { Text("Publicar") }
    }
}

@Composable
private fun ThankYouScreen(modifier: Modifier = Modifier, onRestart: () -> Unit)
{
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text("¡Gracias por compartir tu opinión!", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        Button(onClick = onRestart)
        { Text("Volver") }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewReviewApp()
{
    MaterialTheme { Surface(Modifier.fillMaxSize()) { ReviewAppScreen() } }
}
