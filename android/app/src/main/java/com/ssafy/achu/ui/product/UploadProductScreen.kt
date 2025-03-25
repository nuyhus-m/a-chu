package com.ssafy.achu.ui.product

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.achu.R
import com.ssafy.achu.core.components.BasicTextField
import com.ssafy.achu.core.components.CenterTopAppBar
import com.ssafy.achu.core.components.PointBlueButton
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.LightGray
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.White

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UploadProductScreen() {
    val space = 24.dp
    val smallSpace = 8.dp

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {

        // 탑바
        CenterTopAppBar(
            title = stringResource(R.string.upload_product),
            onBackClick = {}
        )

        // 내용
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState)
        ) {
            // 이미지 추가
            AddImageIcon()
            Spacer(modifier = Modifier.height(space))

            // 제목
            Text(
                text = stringResource(R.string.title),
                style = AchuTheme.typography.semiBold18
            )
            Spacer(modifier = Modifier.height(smallSpace))
            BasicTextField(
                value = "",
                onValueChange = {},
                placeholder = stringResource(R.string.writing_title),
                placeholderColor = LightGray,
                borderColor = PointBlue,
                radius = 8
            )
            Spacer(modifier = Modifier.height(space))

            // 카테고리
            Text(
                text = stringResource(R.string.category),
                style = AchuTheme.typography.semiBold18
            )
            Spacer(modifier = Modifier.height(smallSpace))
            CategoryDropdown(onCategorySelected = {})
            Spacer(modifier = Modifier.height(space))

            // 가격
            Text(
                text = stringResource(R.string.price),
                style = AchuTheme.typography.semiBold18
            )
            Spacer(modifier = Modifier.height(smallSpace))
            PriceInputField(
                value = "",
                onValueChange = {}
            )
            Spacer(modifier = Modifier.height(space))

            // 자세한 설명
            Text(
                text = stringResource(R.string.detail_description),
                style = AchuTheme.typography.semiBold18
            )
            Spacer(modifier = Modifier.height(smallSpace))
            DescriptionInputField(
                value = "",
                onValueChange = {}
            )
            Spacer(modifier = Modifier.height(space))

            // 누가 쓰던 물건인가요?
            Text(
                text = stringResource(R.string.baby_question),
                style = AchuTheme.typography.semiBold18
            )
            Spacer(modifier = Modifier.height(smallSpace))
            BabyDropdown(onBabySelected = {})
            Spacer(modifier = Modifier.height(space))
        }

        // 바텀바
        Column(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 40.dp, start = 24.dp, end = 24.dp)
                .navigationBarsPadding()
                .background(color = Color.White)
        ) {
            PointBlueButton(
                buttonText = stringResource(R.string.write_complete),
                onClick = {}
            )
        }
    }
}

@Composable
fun AddImageIcon(
    imgNumber: Int = 0
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.25f)
            .aspectRatio(1f)
            .border(width = 1.dp, color = PointBlue, shape = RoundedCornerShape(8.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_camera),
            contentDescription = stringResource(R.string.add_img),
            tint = LightGray,
            modifier = Modifier.size(36.dp)
        )
        Text(
            text = "${imgNumber}/5",
            style = AchuTheme.typography.regular16.copy(color = LightGray)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    modifier: Modifier = Modifier,
    label: String = stringResource(R.string.category),
    onCategorySelected: (String) -> Unit
) {
    val categories = listOf("의류", "가전제품", "가구", "도서", "스포츠/레저", "화장품", "기타")
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = selectedCategory,
                onValueChange = {},
                readOnly = true,
                label = { Text(label) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = PointBlue,
                    focusedBorderColor = PointBlue,
                    unfocusedLabelColor = LightGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                shape = RoundedCornerShape(8.dp)
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            selectedCategory = category
                            expanded = false
                            onCategorySelected(category)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PriceInputField(
    value: String,
    onValueChange: (String) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .onFocusChanged { focusState -> isFocused = focusState.isFocused },
        textStyle = AchuTheme.typography.regular16,
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PointBlue,
            unfocusedBorderColor = PointBlue,
            cursorColor = Color.Black,
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_won),
                contentDescription = stringResource(R.string.won),
                modifier = Modifier.size(18.dp),
                tint = if (isFocused) Color.Black else LightGray
            )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.enter_price),
                style = AchuTheme.typography.regular16.copy(color = LightGray)
            )
        }
    )
}

@Composable
fun DescriptionInputField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = AchuTheme.typography.regular16,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PointBlue,
            unfocusedBorderColor = PointBlue,
            cursorColor = Color.Black,
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.enter_description),
                style = AchuTheme.typography.regular16.copy(color = LightGray)
            )
        },
        minLines = 5
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BabyDropdown(
    modifier: Modifier = Modifier,
    onBabySelected: (String) -> Unit
) {
    val babyList = listOf("강두식", "강삼식")
    var expanded by remember { mutableStateOf(false) }
    var selectedBaby by remember { mutableStateOf("강두식") }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = selectedBaby,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = PointBlue,
                    focusedBorderColor = PointBlue,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                shape = RoundedCornerShape(8.dp)
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                babyList.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            selectedBaby = category
                            expanded = false
                            onBabySelected(category)
                        }
                    )
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun UploadProductScreenPreview() {
    AchuTheme {
        UploadProductScreen()
    }
}