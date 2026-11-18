package com.example.estado_claire.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.estado_claire.model.Priority
import com.example.estado_claire.model.State
import com.example.estado_claire.model.Task

@Composable
fun TaskTag(
    modifier: Modifier = Modifier,
    task: Task,
    onToggle: () -> Unit,
    onDelete: () -> Unit
)
{
    Column(modifier.fillMaxWidth().heightIn(min = 64.dp))
    {
        HorizontalDivider(color = Color.Gray.copy(alpha = 0.5f))

        Row(
            Modifier.fillMaxWidth().padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            Checkbox(
                checked = task.state == State.Ready,
                onCheckedChange = { onToggle() }
            )

            Column(Modifier.weight(1f))
            {
                Text(
                    text = task.name,
                    fontSize = 18.sp,
                    textDecoration = if (task.state == State.Ready) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (task.state == State.Ready) Color.Gray else Color.Unspecified
                )

                val (bgColor, textColor) = when (task.priority) {
                    Priority.Low -> Color(0x4400CC08) to Color(0xFF008824)
                    Priority.Mid -> Color(0x44FFFF00) to Color(0xFFFFAA00)
                    Priority.Hi -> Color(0x44FF1100) to Color(0xFF893300)
                }

                Text(
                    text = task.priority.nom,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .background(bgColor)
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                    color = textColor,
                    fontSize = 12.sp
                )
            }

            IconButton(onClick = onDelete)
            {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Eliminar",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }

        HorizontalDivider(color = Color.Gray.copy(alpha = 0.5f))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskTag()
{
    MaterialTheme()
    {
        Surface()
        {
            Column()
            {
                TaskTag(
                    task = Task(name = "Entregar el proyecto de OOP", priority = Priority.Hi),
                    onToggle = {},
                    onDelete = {}
                )
                TaskTag(
                    task = Task(name = "Hacer la práctica de MVVM", priority = Priority.Mid, state = State.Ready),
                    onToggle = {},
                    onDelete = {}
                )
            }
        }
    }
}