package com.ssafy.achu.core.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontBlue

@Composable
fun LabelWithErrorMsg(
    label: String,
    errorMessage: String,
    isBold: Boolean = false,
    enabled: Boolean = false
) {
    Row {
        Text(
            text = label,
            style = if (isBold) AchuTheme.typography.semiBold18 else AchuTheme.typography.regular18
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = errorMessage,
            style = AchuTheme.typography.regular12.copy(color = if (enabled) FontBlue else Color.Red)
        )
    }
}