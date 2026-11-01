package com.example.estado_claire.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.estado_claire.R

@Composable
fun Header(modifier: Modifier = Modifier, text: String, subtitle: String)
{
    Column(modifier)
    {
        Text(text, fontSize = 32.sp)
        Spacer(Modifier.padding(8.dp))
        Text(subtitle, fontSize = 16.sp)
        Spacer(Modifier.padding(8.dp))
    }
}

@Preview
@Composable
fun PreviewHeader()
{
    Surface()
    {
        Header(Modifier.fillMaxWidth(),
        stringResource(R.string.talleres_header_label),
        stringResource(R.string.talleres_header_subtitle)
        )
    }
}
