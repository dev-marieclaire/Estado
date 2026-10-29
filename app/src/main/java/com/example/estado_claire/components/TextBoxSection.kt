package com.example.estado_claire.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.estado_claire.logic.TextoEstado
import com.example.estado_claire.logic.TipoFuente

@Composable
fun TextBoxSection(
    modifier: Modifier = Modifier,
    title: String,
    textState: TextoEstado
)
{
    Column(
        modifier,
        Arrangement.spacedBy(8.dp)
    )
    {
        Text(title)
        Box(Modifier.fillMaxSize())
        {
            Text(
                "Era una noche tranquila en el pueblo cuando las campanas comenzaron a sonar, y nadie supo por qué.",
                fontSize = TextUnit(textState.tamanoFuente, TextUnitType.Sp),
                letterSpacing = TextUnit(textState.tamanoInterlineado.multiplier, TextUnitType.Sp),
                fontFamily =
                    if(textState.tipoFuente == TipoFuente.SERIF) FontFamily.Serif
                    else FontFamily.SansSerif,
                color = if (textState.modoOscuro == true) Color.White
                        else Color.Black,
                modifier = Modifier
                    .fillMaxSize(1f)
                    .background(
                        color = if (textState.modoOscuro == true) Color.Black
                                else Color.White
                    )
                    .padding(24.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewTextBoxSection()
{
    Surface()
    {
        var textoEstado = TextoEstado();
        TextBoxSection(
            Modifier.fillMaxSize(),
            "Preview",
            textState = textoEstado)
    }
}