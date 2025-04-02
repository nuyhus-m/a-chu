package com.ssafy.achu.ui.product.uploadproduct

import com.ssafy.achu.core.util.Constants.SALE
import com.ssafy.achu.data.model.baby.BabyResponse
import com.ssafy.achu.data.model.product.CategoryResponse
import okhttp3.MultipartBody

data class UploadProductUiState(
    val title: String = "",
    val selectedCategory: CategoryResponse? = null,
    val price: String = "",
    val categories: List<CategoryResponse> = emptyList(),
    val description: String = "",
    val selectedBaby: BabyResponse? = null,
    val selectedImages: List<MultipartBody.Part> = emptyList(),

    val titleErrorMessage: String = "",
    val descriptionErrorMessage: String = "",

    val priceCategory: String = SALE,

    val isImageValid: Boolean = false,
    val isTitleValid: Boolean = false,
    val isPriceValid: Boolean = false,
    val isCategoryValid: Boolean = false,
    val isDescriptionValid: Boolean = false,
    val isBabyValid: Boolean = false,

    val buttonState: Boolean = false
)