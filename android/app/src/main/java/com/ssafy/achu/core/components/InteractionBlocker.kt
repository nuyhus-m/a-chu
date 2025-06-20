package com.ssafy.achu.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.zIndex

@Composable
fun InteractionBlocker(isBlocking: Boolean) {
    if (isBlocking) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(999f)
                .background(Color.Transparent) // 시각적으로는 아무것도 없음
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            awaitPointerEvent()
                        }
                    }
                }
        )
    }
}
