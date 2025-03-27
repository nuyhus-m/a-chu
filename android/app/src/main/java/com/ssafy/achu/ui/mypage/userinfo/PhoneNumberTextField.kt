import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import com.ssafy.achu.R

@Composable
fun PhoneNumberTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "전화번호 입력",
    modifier: Modifier = Modifier,
    icon: Int = R.drawable.ic_write,
    pointColor: Color
) {
    val numericRegex = Regex("[^0-9]")
    val onPhoneNumberChange: (String) -> Unit = { input ->
        val stripped = numericRegex.replace(input, "")
        onValueChange(
            if (stripped.length >= 11) {
                stripped.substring(0..10)
            } else {
                stripped
            }
        )
    }

    // InteractionSource 생성
    val interactionSource = remember { MutableInteractionSource() }

    // 포커스 상태 추적
    val isFocused by interactionSource.collectIsFocusedAsState()

    // 플레이스홀더 상태
    val placeholderChange = if (isFocused) "-제외하고 입력" else placeholder

    OutlinedTextField(
        value = value,
        onValueChange = onPhoneNumberChange,
        modifier = modifier.height(50.dp),
        singleLine = true,
        shape = RoundedCornerShape(30.dp),
        placeholder = {
            Text(
                text = placeholderChange
            )
        },
        visualTransformation = PhoneNumberVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = pointColor,
            unfocusedBorderColor = pointColor,
            cursorColor = Color.Black,
            focusedLabelColor = pointColor,
            unfocusedLabelColor = pointColor
        ),
        trailingIcon = {
            Icon(
                painter = painterResource(icon),
                tint = pointColor,
                contentDescription = "Clear text"
            )
        },

        interactionSource = interactionSource

    )
}

// 전화번호 포맷 함수
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
