package com.ssafy.achu.ui.product.productlist

import com.ssafy.achu.data.model.product.CategoryResponse
import com.ssafy.achu.data.model.product.ProductResponse

data class ProductListUIState(
    val query: String = "",
    val categories: List<CategoryResponse> = emptyList(),
    val selectedCategoryId: Int = 0,
    val products: List<ProductResponse> = emptyList(),
    val currentOffset: Int = 0,
    val isLastPage: Boolean = false
)
