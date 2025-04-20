package com.ssafy.achu.ui.product.uploadproduct

import android.net.Uri
import com.ssafy.achu.core.util.Constants.SALE
import com.ssafy.achu.data.model.baby.BabyResponse
import com.ssafy.achu.data.model.product.CategoryResponse

data class UploadProductUiState(
    val title: String = "",
    val selectedCategory: CategoryResponse? = null,
    val price: String = "",
    val categories: List<CategoryResponse> = emptyList(),
    val description: String = "",
    val selectedBaby: BabyResponse? = null,
    val imgUris: List<Uri> = emptyList(),

    val titleErrorMessage: String = "",
    val descriptionErrorMessage: String = "",

    val priceCategory: String = SALE,

    val isImageValid: Boolean = false,
    val isTitleValid: Boolean = false,
    val isPriceValid: Boolean = false,
    val isCategoryValid: Boolean = false,
    val isDescriptionValid: Boolean = false,
    val isBabyValid: Boolean = false,

    val showUploadDialog: Boolean = false,

    )