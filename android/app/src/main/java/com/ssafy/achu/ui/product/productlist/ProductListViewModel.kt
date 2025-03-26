package com.ssafy.achu.ui.product.productlist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductListViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUIState())
    val uiState: StateFlow<ProductListUIState> = _uiState.asStateFlow()

    fun updateQuery(query: String) {
        _uiState.update { it.copy(query = query) }
    }
}