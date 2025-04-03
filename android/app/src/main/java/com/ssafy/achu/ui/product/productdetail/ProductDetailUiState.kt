package com.ssafy.achu.ui.product.productdetail

data class ProductDetailUiState(
    val showDeleteDialog: Boolean = false,
    val showUploadDialog: Boolean = false,
    val isDeleteSuccess: Boolean = false,
)