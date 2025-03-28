package com.ssafy.achu.core.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 13) text.text.substring(0..12) else text.text

        var out = ""
        for (i in trimmed.indices) {
            if (i == 3 || i == 7) out += "-"
            out += trimmed[i]
        }

        return TransformedText(AnnotatedString(out), phoneNumberOffsetMapping)
    }

    private val phoneNumberOffsetMapping = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return when (offset) {
                in 0..3 -> offset
                in 4..7 -> offset + 1
                in 8..11 -> offset + 2
                else -> offset + 2
            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            return when (offset) {
                in 0..3 -> offset
                in 4..8 -> offset - 1
                in 9..12 -> offset - 2
                else -> offset - 2
            }
        }
    }
}