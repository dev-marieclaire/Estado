package com.example.estado_claire

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.estado_claire.components.CalcButton

enum class Operacion { NONE, ADD, SUB, MUL, DIV }
fun operate(operation_type: Operacion, a: Double, b: Double): Double
{
    if (operation_type == Operacion.ADD) return a + b
    else if (operation_type == Operacion.SUB) return a - b
    else if (operation_type == Operacion.MUL) return a * b
    else if (operation_type == Operacion.DIV) return a / b

    return 0.0; // Técnicamente, está fuera de rango.
}

@Composable
fun InicioScreen(modifier: Modifier, nombre: String)
{
    var operand by rememberSaveable { mutableStateOf(0.0) }
    var num_ecris by rememberSaveable { mutableStateOf("") }

    var operation by rememberSaveable { mutableStateOf(Operacion.NONE) }

    Column(modifier = modifier.padding(16.dp))
    {
        TextField(
            value = num_ecris,
            onValueChange = { num_ecris = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(192.dp)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Row(modifier = Modifier.fillMaxSize())
        {
            Column(modifier = Modifier.weight(3/4f))
            {
                Row(modifier = Modifier.weight(1f))
                {
                    CalcButton(text = "C", modifier = Modifier.weight(2/3f))
                    {
                        num_ecris = ""
                        operation = Operacion.NONE
                    }
                    CalcButton(text = "<-", modifier = Modifier.weight(1/3f))
                    {
                        num_ecris = num_ecris.dropLast(1)
                    }
                }
                Row(modifier = Modifier.weight(1f))
                {
                    CalcButton(text = "1", modifier = Modifier.weight(1f).aspectRatio(1f)) { num_ecris += "1" }
                    CalcButton(text = "2", modifier = Modifier.weight(1f).aspectRatio(1f)) { num_ecris += "2" }
                    CalcButton(text = "3", modifier = Modifier.weight(1f).aspectRatio(1f)) { num_ecris += "3" }
                }
                Row(modifier = Modifier.weight(1f))
                {
                    CalcButton(text = "4", modifier = Modifier.weight(1f).aspectRatio(1f)) { num_ecris += "4" }
                    CalcButton(text = "5", modifier = Modifier.weight(1f).aspectRatio(1f)) { num_ecris += "5" }
                    CalcButton(text = "6", modifier = Modifier.weight(1f).aspectRatio(1f)) { num_ecris += "6" }
                }
                Row(modifier = Modifier.weight(1f))
                {
                    CalcButton(text = "7", modifier = Modifier.weight(1f).aspectRatio(1f)) { num_ecris += "7" }
                    CalcButton(text = "8", modifier = Modifier.weight(1f).aspectRatio(1f)) { num_ecris += "8" }
                    CalcButton(text = "9", modifier = Modifier.weight(1f).aspectRatio(1f)) { num_ecris += "9" }
                }
                Row(modifier = Modifier.weight(1f))
                {
                    CalcButton(text = "0", modifier = Modifier.weight(2/3f)) { num_ecris += "0" }
                    CalcButton(text = ".", modifier = Modifier.weight(1/3f).aspectRatio(1f)) { num_ecris += "." }
                }
            }
            Column(modifier = Modifier.weight(1/4f))
            {
                CalcButton(text = "+", modifier = Modifier.weight(1f))
                {
                    val actual = num_ecris.toDoubleOrNull()
                    if (actual != null)
                    {
                        operand =   if (operation == Operacion.NONE) actual
                                    else operate(operation, operand, actual)
                        num_ecris = ""
                        operation = Operacion.ADD
                    }
                }
                CalcButton(text = "-", modifier = Modifier.weight(1f))
                {
                    val actual = num_ecris.toDoubleOrNull()
                    if (actual != null)
                    {
                        operand =   if (operation == Operacion.NONE) actual
                        else operate(operation, operand, actual)
                        num_ecris = ""
                        operation = Operacion.SUB
                    }
                }
                CalcButton(text = "*", modifier = Modifier.weight(1f))
                {
                    val actual = num_ecris.toDoubleOrNull()
                    if (actual != null)
                    {
                        operand =   if (operation == Operacion.NONE) actual
                        else operate(operation, operand, actual)
                        num_ecris = ""
                        operation = Operacion.MUL
                    }
                }
                CalcButton(text = "/", modifier = Modifier.weight(1f))
                {
                    val actual = num_ecris.toDoubleOrNull()
                    if (actual != null)
                    {
                        operand =   if (operation == Operacion.NONE) actual
                        else operate(operation, operand, actual)
                        num_ecris = ""
                        operation = Operacion.DIV
                    }
                }
                CalcButton(text = "=", modifier = Modifier.weight(1f))
                {
                    num_ecris = if (num_ecris != "") operate(operation, operand, num_ecris.toDouble()).toString()
                                else operand.toString()
                }
            }
        }
    }
}
