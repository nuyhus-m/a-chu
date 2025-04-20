package com.ssafy.achu.ui.product.uploadproduct

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.text.trimmedLength
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
import com.ssafy.achu.data.model.product.ModifyProductRequest
import com.ssafy.achu.data.model.product.ProductDetailResponse
import com.ssafy.achu.data.model.product.Seller
import com.ssafy.achu.data.model.product.UploadProductRequest
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage: SharedFlow<String> = _toastMessage.asSharedFlow()

    private val _isModifySuccess = MutableSharedFlow<Boolean>()
    val isModifySuccess: SharedFlow<Boolean> = _isModifySuccess.asSharedFlow()

    private val _navigateEvents = MutableSharedFlow<Boolean>()
    val navigateEvents: SharedFlow<Boolean> = _navigateEvents.asSharedFlow()



    private val titleRegex = Regex("^.{0,18}$")
    private val descriptionRegex = Regex("^.{0,198}$")
    private val priceRegex = Regex("^[0-9]{1,10}$")
    private val tempRegex = Regex("""^[^"\\]*$""")

    init {
        getCategoryList()
    }


    fun updateShowUploadDialog(dialogState: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                showUploadDialog = dialogState
            )
        }
    }

    fun updateImageUris(uris: List<Uri>) {
        _uiState.update { currentState ->
            currentState.copy(imgUris = uris)
        }
    }

    fun updateTitle(title: String) {
        _uiState.update { currentState ->
            currentState.copy(
                title = title,
                titleErrorMessage =
                    if (!tempRegex.matches(title)) {
                        "* \" 또는 \\ 는 포함할 수 없습니다."
                    } else {
                        ""
                    },
                isTitleValid = titleRegex.matches(title) && tempRegex.matches(title)
            )
        }

    }

    fun updatePrice(price: String) {
        val isNumeric = priceRegex.matches(price) && price.isNotBlank()
        val priceValue = price.toLongOrNull()
        val isUnderLimit = priceValue != null && priceValue <= 1_000_000_000

        _uiState.update { currentState ->
            currentState.copy(
                price = price.trim(),
                isPriceValid = isNumeric && isUnderLimit,
                priceCategory = if (price == "0") DONATION else SALE
            )
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

    }

    fun updateDescription(description: String) {
        _uiState.update { currentState ->
            currentState.copy(
                description = description,
                descriptionErrorMessage =
                    if (!tempRegex.matches(description)) {
                        "* \" 또는 \\ 는 포함할 수 없습니다."
                    } else {
                        ""
                    },
                isDescriptionValid = descriptionRegex.matches(description) && tempRegex.matches(
                    description
                )
            )
        }

    }

    fun updateSelectedCategory(category: CategoryResponse) {
        _uiState.update { currentState ->
            currentState.copy(selectedCategory = category)
        }

    }

    fun updateSelectedBaby(baby: BabyResponse) {
        _uiState.update { currentState ->
            currentState.copy(selectedBaby = baby)
        }

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
            price = uiState.value.price.toLong(),
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
            price = uiState.value.price.toLong(),
            title = uiState.value.title
        )
    }

    fun modifyProduct(
        productId: Int
    ) {
        val request = ModifyProductRequest(
            categoryId = uiState.value.selectedCategory!!.id,
            description = uiState.value.description.trim(),
            price = uiState.value.price.trim().toLong(),
            title = uiState.value.title.trim()
        )
        viewModelScope.launch {
            productRepository.modifyProduct(
                productId,
                request
            ).onSuccess { response ->
                Log.d(TAG, "modifyProduct: $response")
                if (response.result == SUCCESS) {
                    _toastMessage.emit("수정이 완료되었습니다.")
                    _isModifySuccess.emit(true)
                }
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "modifyProduct errorResponse: $errorResponse")
                Log.d(TAG, "modifyProduct error: ${it.message}")
                _toastMessage.emit(errorResponse.message)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatCurrentDate(): String {
        val currentDate = LocalDate.now() // 현재 날짜 가져오기
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd") // 원하는 형식 지정
        val formattedDate = currentDate.format(formatter) // 날짜 포맷 적용

        return formattedDate
    }

//    private fun updateButtonState() {
//        _uiState.update { currentState ->
//            currentState.copy(
//                buttonState = currentState.isTitleValid && currentState.isDescriptionValid && currentState.isPriceValid
//                        && currentState.selectedCategory != null && currentState.selectedBaby != null && currentState.imgUris.isNotEmpty()
//            )
//        }
//    }
//
//    private fun updateModifyButtonState() {
//        _uiState.update { currentState ->
//            currentState.copy(
//                buttonState = currentState.isTitleValid && currentState.isDescriptionValid && currentState.isPriceValid
//                        && currentState.selectedCategory != null
//            )
//        }
//    }

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


    fun uploadProduct(
        uploadProductRequest: UploadProductRequest,
        images: List<MultipartBody.Part>,
        isWithMemory: Boolean
    ) {
        viewModelScope.launch {
            productRepository.uploadProduct(
                images = images,
                request = uploadProductRequest,
            ).onSuccess { response ->
                Log.d(TAG, "uploadProduct: $response")
                if (response.result == SUCCESS) {
                    _navigateEvents.emit(isWithMemory)
                    updateShowUploadDialog(false)
                }
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "uploadProduct errorResponse: $errorResponse")
                Log.d(TAG, "uploadProduct error: ${it.message}")
                _toastMessage.emit(errorResponse.message)
                updateShowUploadDialog(false)
            }
        }
    }
}