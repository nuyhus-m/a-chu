package com.ssafy.achu.core.components.textfield

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.ssafy.achu.core.components.LabelWithErrorMsg
import com.ssafy.achu.core.components.PointBlueFlexibleBtn
import com.ssafy.achu.core.theme.PointBlue

@Composable
fun TextFieldWithLabelAndBtn(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    buttonText: String,
    errorMessage: String = "",
    onClick: () -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    enabled: Boolean = false,
    buttonEnabled: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column {
        LabelWithErrorMsg(label, errorMessage, enabled)

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    placeholder = placeholder,
                    placeholderColor = PointBlue,
                    borderColor = PointBlue,
                    keyboardOptions = keyboardOptions,
                    visualTransformation = visualTransformation
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            PointBlueFlexibleBtn(
                buttonText = buttonText,
                onClick = onClick,
                buttonEnabled = buttonEnabled
            )
        }
    }
}