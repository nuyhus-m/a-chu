package com.ssafy.achu.ui.product.uploadproduct

import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import com.ssafy.achu.R
import com.ssafy.achu.core.components.CenterTopAppBar
import com.ssafy.achu.core.components.LabelWithErrorMsg
import com.ssafy.achu.core.components.PointBlueButton
import com.ssafy.achu.core.components.PointBlueLineBtn
import com.ssafy.achu.core.components.textfield.BasicTextField
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.LightGray
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.core.util.Constants.DONATION
import com.ssafy.achu.core.util.Constants.SALE
import com.ssafy.achu.core.util.isImageValid
import com.ssafy.achu.core.util.uriToMultipart
import com.ssafy.achu.data.model.baby.BabyResponse
import com.ssafy.achu.data.model.product.CategoryResponse
import com.ssafy.achu.ui.ActivityViewModel
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "UploadProductScreen"
const val maxTitleLength = 20
const val maxDescriptionLength = 200

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UploadProductScreen(
    modifier: Modifier = Modifier,
    viewModel: UploadProductViewModel = viewModel(),
    activityViewModel: ActivityViewModel,
    isModify: Boolean,
    onBackClick: () -> Unit,
    onNavigateToDetail: () -> Unit = {}
) {

    val context = LocalContext.current
    val space = 24.dp
    val smallSpace = 8.dp

    val uiState by viewModel.uiState.collectAsState()
    val activityUiState by activityViewModel.uiState.collectAsState()

    val scrollState = rememberScrollState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        val validUris = uris.filter { uri -> isImageValid(context, uri) }

        // 유효한 이미지가 없으면 리턴
        if (validUris.isEmpty()) return@rememberLauncherForActivityResult

        if (uiState.imgUris.size + validUris.size <= 3) {
            viewModel.updateImageUris(uiState.imgUris + validUris)
        } else {
            Toast.makeText(context, context.getString(R.string.image_max_3), Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        if (isModify) {
            viewModel.updateTitle(activityUiState.product.title)
            viewModel.updateDescription(activityUiState.product.description)
            viewModel.updateSelectedCategory(activityUiState.product.category)
            viewModel.updatePrice(activityUiState.product.price.toString())
        }
    }

    LaunchedEffect(Unit) {
        if (!isModify) {
            activityUiState.selectedBaby?.let {
                viewModel.updateSelectedBaby(it)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collectLatest { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.isModifySuccess.collectLatest {
            if (it) {
                activityViewModel.getProductDetail(activityUiState.product.id)
            }
        }
    }

    LaunchedEffect(Unit) {
        activityViewModel.errorMessage.collectLatest {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        activityViewModel.getProductSuccess.collectLatest {
            if (it) {
                onBackClick()
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = White)
    ) {

        // 탑바
        CenterTopAppBar(
            title = stringResource(R.string.upload_product),
            onBackClick = onBackClick
        )

        // 내용
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState)
        ) {
            if (!isModify) {
                // 이미지 추가
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(100.dp)
                ) {
                    AddImageIcon(
                        imgNumber = uiState.imgUris.size,
                        onClick = { launcher.launch("image/*") }
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    LazyRow {
                        itemsIndexed(uiState.imgUris) { index, uri ->
                            ImageItem(
                                uri = uri,
                                isFirst = index == 0,
                                onDelete = {
                                    viewModel.updateImageUris(
                                        uiState.imgUris.toMutableList().apply { removeAt(index) }
                                    )
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(space))
            }

            // 제목
            LabelWithErrorMsg(
                label = stringResource(R.string.title),
                errorMessage = uiState.titleErrorMessage,
                isBold = true,
                enabled = uiState.isTitleValid
            )
            Spacer(modifier = Modifier.height(smallSpace))
            BasicTextField(
                value = uiState.title,
                onValueChange = { if (it.length <= maxTitleLength) viewModel.updateTitle(it) },
                placeholder = stringResource(R.string.writing_title),
                placeholderColor = LightGray,
                borderColor = PointBlue,
                radius = 8,
                trailingIcon = {
                    Text(
                        text = "${uiState.title.length}/$maxTitleLength",
                        style = AchuTheme.typography.regular14.copy(color = PointBlue),
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            )
            Spacer(modifier = Modifier.height(space))

            // 카테고리
            Text(
                text = stringResource(R.string.category),
                style = AchuTheme.typography.semiBold18
            )
            Spacer(modifier = Modifier.height(smallSpace))
            CategoryDropdown(
                categories = uiState.categories,
                selectedCategory = uiState.selectedCategory?.name ?: "",
                onCategorySelected = { viewModel.updateSelectedCategory(it) }
            )
            Spacer(modifier = Modifier.height(space))

            // 가격
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.trade_type),
                    style = AchuTheme.typography.semiBold18
                )
                Spacer(modifier = Modifier.width(smallSpace))
                PointBlueLineBtn(
                    buttonText = stringResource(R.string.sale),
                    isSelected = uiState.priceCategory == SALE,
                    onClick = { viewModel.updatePriceCategory(SALE) }
                )
                Spacer(modifier = Modifier.width(smallSpace))
                PointBlueLineBtn(
                    buttonText = stringResource(R.string.donation),
                    isSelected = uiState.priceCategory == DONATION,
                    onClick = { viewModel.updatePriceCategory(DONATION) }
                )
            }
            Spacer(modifier = Modifier.height(smallSpace))
            PriceInputField(
                value = uiState.price,
                onValueChange = {
                    val filtered = it.replace(Regex("[^0-9]"), "")                    // 빈 값이면 0 처리 등 선택적으로 처리 가능
                    val intValue = filtered.toIntOrNull()
                    if (filtered.length >= 11 || (intValue != null && intValue < 0)) {
                        Toast.makeText(context, "가격은 0이상 10억 미만만 가능 합니다", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.updatePrice(filtered)
                    }
                },
                readOnly = uiState.priceCategory == DONATION
            )
            Spacer(modifier = Modifier.height(space))

            // 자세한 설명
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LabelWithErrorMsg(
                    label = stringResource(R.string.detail_description),
                    errorMessage = uiState.descriptionErrorMessage,
                    isBold = true,
                    enabled = uiState.isDescriptionValid
                )
                Text(
                    text = "${uiState.description.length}/$maxDescriptionLength",
                    style = AchuTheme.typography.regular14.copy(color = PointBlue),
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp)
                )
            }
            Spacer(modifier = Modifier.height(smallSpace))
            DescriptionInputField(
                value = uiState.description,
                onValueChange = {
                    val lineCount = it.count { char -> char == '\n' } + 1
                    if (it.length <= maxDescriptionLength && lineCount <= 10) {
                        viewModel.updateDescription(it)
                    }
                }
            )
            Spacer(modifier = Modifier.height(space))

            if (!isModify) {
                // 누가 쓰던 물건인가요?
                Text(
                    text = stringResource(R.string.baby_question),
                    style = AchuTheme.typography.semiBold18
                )
                Spacer(modifier = Modifier.height(smallSpace))
                BabyDropdown(
                    babyList = activityUiState.babyList,
                    selectedBaby = uiState.selectedBaby?.nickname ?: "",
                    onBabySelected = { viewModel.updateSelectedBaby(it) }
                )
                Spacer(modifier = Modifier.height(space))
            }
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
                onClick = {
                    if (!isModify) {
                        onNavigateToDetail()
                        // 모든 이미지를 멀티파트로 변환
                        val multipartFiles =
                            uiState.imgUris.mapNotNull { uri ->
                                uriToMultipart(
                                    context,
                                    uri,
                                    "images"
                                )
                            }
                        viewModel.updateSelectedImages(multipartFiles)
                        Log.d(TAG, "UploadProductScreen: ${viewModel.multipartImages.size}")
                        val product = viewModel.uiStateToProductDetailResponse(
                            activityUiState.user?.nickname ?: "",
                            activityUiState.user?.profileImageUrl ?: ""
                        )
                        activityViewModel.saveProductDetail(product, uiState.imgUris)
                        activityViewModel.saveProductInfo(
                            uploadProductRequest = viewModel.uiStateToUploadProductRequest(),
                            multiPartImages = viewModel.multipartImages,
                            babyName = uiState.selectedBaby?.nickname ?: ""
                        )
                    } else {
                        viewModel.modifyProduct(activityUiState.product.id)
                    }
                },
                enabled = uiState.buttonState
            )
        }
    }
}

@Composable
fun AddImageIcon(
    imgNumber: Int = 0,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .size(80.dp)
            .aspectRatio(1f)
            .border(width = 1.dp, color = PointBlue, shape = RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
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
            text = "${imgNumber}/3",
            style = AchuTheme.typography.regular16.copy(color = LightGray)
        )
    }
}

@Composable
fun ImageItem(uri: Uri, onDelete: () -> Unit = {}, isFirst: Boolean = false) {
    Box(
        modifier = Modifier
            .size(100.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .aspectRatio(1f)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(8.dp)),
        ) {
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = stringResource(R.string.selected_image),
                contentScale = ContentScale.Crop
            )

            // "대표사진" 라벨 (첫 번째 아이템에만)
            if (isFirst) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(
                            Color.Black.copy(alpha = 0.6f),
                            RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                        )
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.first_image),
                        style = AchuTheme.typography.regular14.copy(color = Color.White),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // X 버튼 (오른쪽 상단)
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                .clickable { onDelete() }
                .padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.delete),
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    modifier: Modifier = Modifier,
    label: String = stringResource(R.string.category),
    selectedCategory: String,
    categories: List<CategoryResponse>,
    onCategorySelected: (CategoryResponse) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

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
                        text = { Text(category.name) },
                        onClick = {
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
    onValueChange: (String) -> Unit,
    readOnly: Boolean
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
        readOnly = readOnly,
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
            .fillMaxWidth().height(150.dp),
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
        minLines = 5,
        maxLines = 10
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BabyDropdown(
    modifier: Modifier = Modifier,
    babyList: List<BabyResponse>,
    selectedBaby: String,
    onBabySelected: (BabyResponse) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

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
                babyList.forEach { baby ->
                    DropdownMenuItem(
                        text = { Text(baby.nickname) },
                        onClick = {
                            expanded = false
                            onBabySelected(baby)
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
        UploadProductScreen(
            activityViewModel = viewModel(),
            isModify = false,
            onBackClick = {}
        )
    }
}