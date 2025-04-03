package com.ssafy.achu.ui.product.productlist

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ssafy.achu.core.ApplicationClass.Companion.productRepository
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.navigation.BottomNavRoute
import com.ssafy.achu.core.util.Constants.LATEST
import com.ssafy.achu.core.util.Constants.SUCCESS
import com.ssafy.achu.core.util.getErrorResponse
import com.ssafy.achu.data.model.product.CategoryResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "ProductListViewModel"
private const val LIMIT = 20;

class ProductListViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val categoryId = savedStateHandle.toRoute<BottomNavRoute.ProductList>().categoryId

    private val _uiState = MutableStateFlow(ProductListUIState())
    val uiState: StateFlow<ProductListUIState> = _uiState.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage: SharedFlow<String> = _toastMessage.asSharedFlow()

    init {
        getCategoryList()
        updateSelectedCategoryId(categoryId)
    }

    fun updateQuery(query: String) {
        _uiState.update { it.copy(query = query) }
    }

    fun updateSelectedCategoryId(categoryId: Int) {
        _uiState.update { it.copy(selectedCategoryId = categoryId) }
    }

    fun updateCurrentOffset(offset: Int) {
        _uiState.update { it.copy(currentOffset = offset) }
    }

    fun updateIsLastPage(isLastPage: Boolean) {
        _uiState.update { it.copy(isLastPage = isLastPage) }
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

    private fun getProductListByCategory() {
        if (uiState.value.isLastPage) return
        viewModelScope.launch {
            val offset = uiState.value.currentOffset
            productRepository.getProductListByCategory(
                categoryId = uiState.value.selectedCategoryId,
                offset = offset,
                limit = LIMIT,
                sort = LATEST
            ).onSuccess { response ->
                Log.d(TAG, "getProductListByCategory: $response")
                if (response.result == SUCCESS) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            products = if (offset == 0) response.data else currentState.products + response.data,
                            currentOffset = currentState.currentOffset + 1,
                            isLastPage = response.data.size < LIMIT
                        )
                    }
                }
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "getProductListByCategory errorResponse: $errorResponse")
                Log.d(TAG, "getProductListByCategory error: ${it.message}")
            }
        }
    }

    private fun getProductList() {
        if (uiState.value.isLastPage) return
        viewModelScope.launch {
            val offset = uiState.value.currentOffset
            productRepository.getProductList(
                offset = offset,
                limit = LIMIT,
                sort = LATEST
            ).onSuccess { response ->
                Log.d(TAG, "getProductList: $response")
                if (response.result == SUCCESS) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            products = if (offset == 0) response.data else currentState.products + response.data,
                            currentOffset = currentState.currentOffset + 1,
                            isLastPage = response.data.size < LIMIT
                        )
                    }
                }
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "getProductList errorResponse: $errorResponse")
                Log.d(TAG, "getProductList error: ${it.message}")
            }

        }
    }

    private fun searchProductList() {
        if (uiState.value.isLastPage) return
        viewModelScope.launch {
            val offset = uiState.value.currentOffset
            productRepository.searchProduct(
                keyword = uiState.value.query,
                offset = offset,
                limit = LIMIT,
                sort = LATEST
            ).onSuccess { response ->
                Log.d(TAG, "searchProductList: $response")
                if (response.result == SUCCESS) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            products = if (offset == 0) response.data else currentState.products + response.data,
                            currentOffset = currentState.currentOffset + 1,
                            isLastPage = response.data.size < LIMIT
                        )
                    }
                }
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "searchProductList errorResponse: $errorResponse")
                Log.d(TAG, "searchProductList error: ${it.message}")
            }
        }
    }

    private fun searchProductListByCategory() {
        if (uiState.value.isLastPage) return
        viewModelScope.launch {
            val offset = uiState.value.currentOffset
            productRepository.searchProductByCategory(
                categoryId = uiState.value.selectedCategoryId,
                keyword = uiState.value.query,
                offset = offset,
                limit = LIMIT,
                sort = LATEST
            ).onSuccess { response ->
                Log.d(TAG, "searchProductListByCategory: $response")
                if (response.result == SUCCESS) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            products = if (offset == 0) response.data else currentState.products + response.data,
                            currentOffset = currentState.currentOffset + 1,
                            isLastPage = response.data.size < LIMIT
                        )
                    }
                }
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "searchProductListByCategory errorResponse: $errorResponse")
                Log.d(TAG, "searchProductListByCategory error: ${it}")
            }
        }
    }

    fun loadProductList() {
        Log.d(
            TAG, "loadProductList: " +
                    "categoryId = ${uiState.value.selectedCategoryId}," +
                    "query = ${uiState.value.query}" +
                    "offset = ${uiState.value.currentOffset}"
        )

        if (uiState.value.selectedCategoryId == 0) {
            if (uiState.value.query.isEmpty()) {
                getProductList()
            } else {
                searchProductList()
            }
        } else {
            if (uiState.value.query.isEmpty()) {
                getProductListByCategory()
            } else {
                searchProductListByCategory()
            }
        }
    }

    fun likeProduct(productId: Int, index: Int) {
        viewModelScope.launch {
            productRepository.likeProduct(productId)
                .onSuccess { response ->
                    Log.d(TAG, "likeProduct: $response")
                    if (response.result == SUCCESS) {
                        updateLikeProductState(index, true)
                    }
                }.onFailure {
                    val errorResponse = it.getErrorResponse(retrofit)
                    Log.d(TAG, "likeProduct errorResponse: $errorResponse")
                    Log.d(TAG, "likeProduct error: ${it.message}")
                    _toastMessage.emit(errorResponse.message)
                }
        }
    }

    fun unlikeProduct(productId: Int, index: Int) {
        viewModelScope.launch {
            productRepository.unlikeProduct(productId)
                .onSuccess { response ->
                    Log.d(TAG, "unlikeProduct: $response")
                    if (response.result == SUCCESS) {
                        updateLikeProductState(index, false)
                    }
                }.onFailure {
                    val errorResponse = it.getErrorResponse(retrofit)
                    Log.d(TAG, "unlikeProduct errorResponse: $errorResponse")
                    Log.d(TAG, "unlikeProduct error: ${it.message}")
                    _toastMessage.emit(errorResponse.message)
                }
        }
    }

    private fun updateLikeProductState(index: Int, isLiked: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                products = currentState.products.mapIndexed { i, product ->
                    if (i == index) {
                        product.copy(
                            likedByUser = isLiked,
                            likedUsersCount = if (isLiked) product.likedUsersCount + 1 else product.likedUsersCount - 1
                        )
                    } else {
                        product
                    }
                }
            )
        }
    }
}