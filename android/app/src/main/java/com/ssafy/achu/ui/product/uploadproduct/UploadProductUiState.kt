package com.ssafy.achu.ui.product.uploadproduct

import com.ssafy.achu.data.model.baby.BabyResponse
import com.ssafy.achu.data.model.product.CategoryResponse

data class UploadProductUiState(
    val title: String = "",
    val selectedCategory: CategoryResponse? = null,
    val price: String = "",
    val categories: List<CategoryResponse> = emptyList(),
    val description: String = "",
    val selectedBaby: BabyResponse? = null,

    val titleErrorMessage: String = "",
    val descriptionErrorMessage: String = "",

    val isTitleValid: Boolean = false,
    val isPriceValid: Boolean = false,
    val isCategoryValid: Boolean = false,
    val isDescriptionValid: Boolean = false,
    val isBabyValid: Boolean = false,
)