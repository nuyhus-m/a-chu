package com.ssafy.achu.core.components.textfield

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ssafy.achu.R
import com.ssafy.achu.core.components.LabelWithErrorMsg

@Composable
fun PwdTextFieldWithLabel(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    errorMessage: String = ""
) {
    LabelWithErrorMsg(label, errorMessage)

    Spacer(modifier = Modifier.height(8.dp))

    PasswordTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = stringResource(R.string.password_format)
    )
}