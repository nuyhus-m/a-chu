package com.ssafy.achu.ui.product.uploadproduct

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.productRepository
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.util.Constants.DONATION
import com.ssafy.achu.core.util.Constants.SALE
import com.ssafy.achu.core.util.Constants.SUCCESS
import com.ssafy.achu.core.util.getErrorResponse
import com.ssafy.achu.data.model.baby.BabyResponse
import com.ssafy.achu.data.model.product.CategoryResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "UploadProductViewModel"

class UploadProductViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UploadProductUiState())
    val uiState: StateFlow<UploadProductUiState> = _uiState.asStateFlow()

    init {
        getCategoryList()
    }

    fun updateTitle(title: String) {
        _uiState.update { currentState ->
            currentState.copy(
                title = title,
                titleErrorMessage =
                    if (title.isBlank() || title.length < 2 || title.length > 20) {
                        "* 2~20자로 입력해주세요."
                    } else {
                        ""
                    },
                isTitleValid = title.isNotBlank() && title.length in 2..20
            )
        }
        updateButtonState()
    }

    fun updatePrice(price: String) {
        _uiState.update { currentState ->
            currentState.copy(
                price = price,
                isPriceValid = price.isNotBlank(),
                priceCategory = if (price == "0") DONATION else SALE
            )
        }
        updateButtonState()
    }

    fun updatePriceCategory(priceCategory: String) {
        _uiState.update { currentState ->
            currentState.copy(
                priceCategory = priceCategory,
                price = if (priceCategory == DONATION) "0" else currentState.price,
                isPriceValid = priceCategory == DONATION || currentState.price.isNotBlank()
            )
        }
        updateButtonState()
    }

    fun updateDescription(description: String) {
        _uiState.update { currentState ->
            currentState.copy(
                description = description,
                descriptionErrorMessage =
                    if (description.isBlank() || description.length < 2 || description.length > 200) {
                        "* 2~200자로 입력해주세요."
                    } else {
                        ""
                    },
                isDescriptionValid = description.isNotBlank() && description.length in 2..200
            )
        }
        updateButtonState()
    }

    fun updateSelectedCategory(category: CategoryResponse) {
        _uiState.update { currentState ->
            currentState.copy(selectedCategory = category)
        }
        updateButtonState()
    }

    fun updateSelectedBaby(baby: BabyResponse) {
        _uiState.update { currentState ->
            currentState.copy(selectedBaby = baby)
        }
        updateButtonState()
    }

    private fun updateButtonState() {
        _uiState.update { currentState ->
            currentState.copy(
                buttonState = currentState.isTitleValid && currentState.isDescriptionValid && currentState.isPriceValid
                        && currentState.selectedCategory != null && currentState.selectedBaby != null
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