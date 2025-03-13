package com.ssafy.achu.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val LightColorScheme = lightColorScheme(
    primary = PointBlue,
    secondary = PointPink
)

@Composable
fun AchuTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    CompositionLocalProvider(LocalTypography provides Typography) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}

object AchuTheme {
    val typography: MyTypography
        @Composable
        get() = LocalTypography.current
}