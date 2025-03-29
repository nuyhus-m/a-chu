package com.ssafy.achu.ui.product.productlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.productRepository
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.util.Constants.LATEST
import com.ssafy.achu.core.util.Constants.SUCCESS
import com.ssafy.achu.core.util.getErrorResponse
import com.ssafy.achu.data.model.product.CategoryResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "ProductListViewModel"

class ProductListViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUIState())
    val uiState: StateFlow<ProductListUIState> = _uiState.asStateFlow()

    init {
        getCategoryList()
    }

    fun updateQuery(query: String) {
        _uiState.update { it.copy(query = query) }
    }

    fun updateSelectedCategoryId(categoryId: Int) {
        _uiState.update { it.copy(selectedCategoryId = categoryId) }
    }

    private fun getCategoryList() {
        viewModelScope.launch {
            productRepository.getCategoryList()
                .onSuccess { response ->
                    Log.d(TAG, "getCategoryList: $response")
                    if (response.result == SUCCESS) {
                        val categories = response.data.toMutableList()
                        categories.add(0, CategoryResponse(0, "All"))
                        _uiState.update { currentState ->
                            currentState.copy(categories = categories)
                        }
                    }
                }.onFailure {
                    val errorResponse = it.getErrorResponse(retrofit)
                    Log.d(TAG, "getCategoryList errorResponse: $errorResponse")
                    Log.d(TAG, "getCategoryList error: ${it.message}")
                }
        }
    }

    fun getProductListByCategory(categoryId: Int) {
        viewModelScope.launch {
            productRepository.getProductListByCategory(
                categoryId = categoryId,
                offset = 0,
                limit = 20,
                sort = LATEST
            ).onSuccess { response ->
                Log.d(TAG, "getProductListByCategory: $response")
                if (response.result == SUCCESS) {
                    _uiState.update { currentState ->
                        currentState.copy(products = response.data)
                    }
                }
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "getProductListByCategory errorResponse: $errorResponse")
                Log.d(TAG, "getProductListByCategory error: ${it.message}")
            }
        }
    }

    fun getProductList() {
        viewModelScope.launch {
            productRepository.getProductList(
                offset = 0,
                limit = 20,
                sort = LATEST
            ).onSuccess { response ->
                Log.d(TAG, "getProductList: $response")
                if (response.result == SUCCESS) {
                    _uiState.update { currentState ->
                        currentState.copy(products = response.data)
                    }
                }
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "getProductList errorResponse: $errorResponse")
                Log.d(TAG, "getProductList error: ${it.message}")
            }

        }
    }
}