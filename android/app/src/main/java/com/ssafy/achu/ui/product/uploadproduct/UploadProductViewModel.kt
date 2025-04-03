package com.ssafy.achu.ui.product.uploadproduct

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ssafy.achu.core.ApplicationClass.Companion.productRepository
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.navigation.Route
import com.ssafy.achu.core.util.Constants.DONATION
import com.ssafy.achu.core.util.Constants.SALE
import com.ssafy.achu.core.util.Constants.SELLING
import com.ssafy.achu.core.util.Constants.SUCCESS
import com.ssafy.achu.core.util.getErrorResponse
import com.ssafy.achu.data.model.baby.BabyResponse
import com.ssafy.achu.data.model.product.CategoryResponse
import com.ssafy.achu.data.model.product.ProductDetailResponse
import com.ssafy.achu.data.model.product.Seller
import com.ssafy.achu.data.model.product.UploadProductRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val TAG = "UploadProductViewModel"

class UploadProductViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val isModify = savedStateHandle.toRoute<Route.UploadProduct>().isModify

    private val _uiState = MutableStateFlow(UploadProductUiState())
    val uiState: StateFlow<UploadProductUiState> = _uiState.asStateFlow()

    private var _multipartImages = emptyList<MultipartBody.Part>()
    val multipartImages: List<MultipartBody.Part> get() = _multipartImages

    private val titleRegex = Regex("^\\S.{0,18}\\S$")
    private val descriptionRegex = Regex("^\\S.{0,198}\\S$")
    private val priceRegex = Regex("^[0-9]*$")
    private val tempRegex = Regex("""^[^"\\]*$""")

    init {
        getCategoryList()
    }

    fun updateImageUris(uris: List<Uri>) {
        _uiState.update { currentState ->
            currentState.copy(imgUris = uris)
        }
        updateButtonState()
    }

    fun updateTitle(title: String) {
        _uiState.update { currentState ->
            currentState.copy(
                title = title,
                titleErrorMessage =
                    if (!tempRegex.matches(title)) {
                        "* \" 또는 \\ 는 포함할 수 없습니다."
                    } else if (!titleRegex.matches(title)) {
                        "* 앞뒤 공백 없이 2~20자로 입력해주세요."
                    } else {
                        ""
                    },
                isTitleValid = titleRegex.matches(title) && tempRegex.matches(title)
            )
        }
        if (isModify) {
            updateModifyButtonState()
        } else {
            updateButtonState()
        }
    }

    fun updatePrice(price: String) {
        _uiState.update { currentState ->
            currentState.copy(
                price = price,
                isPriceValid = priceRegex.matches(price) && price.isNotBlank(),
                priceCategory = if (price == "0") DONATION else SALE
            )
        }
        if (isModify) {
            updateModifyButtonState()
        } else {
            updateButtonState()
        }
    }

    fun updatePriceCategory(priceCategory: String) {
        _uiState.update { currentState ->
            currentState.copy(
                priceCategory = priceCategory,
                price = if (priceCategory == DONATION) "0" else currentState.price,
                isPriceValid = priceCategory == DONATION || currentState.price.isNotBlank()
            )
        }
        if (isModify) {
            updateModifyButtonState()
        } else {
            updateButtonState()
        }
    }

    fun updateDescription(description: String) {
        _uiState.update { currentState ->
            currentState.copy(
                description = description,
                descriptionErrorMessage =
                    if (!tempRegex.matches(description)) {
                        "* \" 또는 \\ 는 포함할 수 없습니다."
                    } else if (!descriptionRegex.matches(description)) {
                        "* 앞뒤 공백 없이 2~200자로 입력해주세요."
                    } else {
                        ""
                    },
                isDescriptionValid = descriptionRegex.matches(description) && tempRegex.matches(
                    description
                )
            )
        }
        if (isModify) {
            updateModifyButtonState()
        } else {
            updateButtonState()
        }
    }

    fun updateSelectedCategory(category: CategoryResponse) {
        _uiState.update { currentState ->
            currentState.copy(selectedCategory = category)
        }
        if (isModify) {
            updateModifyButtonState()
        } else {
            updateButtonState()
        }
    }

    fun updateSelectedBaby(baby: BabyResponse) {
        _uiState.update { currentState ->
            currentState.copy(selectedBaby = baby)
        }
        updateButtonState()
    }

    fun updateSelectedImages(images: List<MultipartBody.Part>) {
        _multipartImages = images
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun uiStateToProductDetailResponse(
        sellerName: String,
        sellerImgUrl: String
    ): ProductDetailResponse {
        return ProductDetailResponse(
            category = uiState.value.selectedCategory!!,
            createdAt = formatCurrentDate(),
            description = uiState.value.description,
            id = -1,
            imgUrls = emptyList(),
            likedByUser = false,
            likedUsersCount = 0,
            price = uiState.value.price.toInt(),
            seller = Seller(
                id = -1,
                nickname = sellerName,
                imgUrl = sellerImgUrl
            ),
            title = uiState.value.title,
            tradeStatus = SELLING
        )
    }

    fun uiStateToUploadProductRequest(): UploadProductRequest {
        return UploadProductRequest(
            babyId = uiState.value.selectedBaby?.id ?: -1,
            categoryId = uiState.value.selectedCategory?.id ?: -1,
            description = uiState.value.description,
            price = uiState.value.price.toInt(),
            title = uiState.value.title
        )
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatCurrentDate(): String {
        val currentDate = LocalDate.now() // 현재 날짜 가져오기
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd") // 원하는 형식 지정
        val formattedDate = currentDate.format(formatter) // 날짜 포맷 적용

        return formattedDate
    }

    private fun updateButtonState() {
        _uiState.update { currentState ->
            currentState.copy(
                buttonState = currentState.isTitleValid && currentState.isDescriptionValid && currentState.isPriceValid
                        && currentState.selectedCategory != null && currentState.selectedBaby != null && currentState.imgUris.isNotEmpty()
            )
        }
    }

    private fun updateModifyButtonState() {
        _uiState.update { currentState ->
            currentState.copy(
                buttonState = currentState.isTitleValid && currentState.isDescriptionValid && currentState.isPriceValid
                        && currentState.selectedCategory != null
            )
        }
    }

    private fun getCategoryList() {
        viewModelScope.launch {
            productRepository.getCategoryList()
                .onSuccess { response ->
                    Log.d(TAG, "getCategoryList: $response")
                    if (response.result == SUCCESS) {
                        _uiState.update { currentState ->
                            currentState.copy(categories = response.data)
                        }
                    }
                }.onFailure {
                    val errorResponse = it.getErrorResponse(retrofit)
                    Log.d(TAG, "getCategoryList errorResponse: $errorResponse")
                    Log.d(TAG, "getCategoryList error: ${it.message}")
                }
        }
    }
}