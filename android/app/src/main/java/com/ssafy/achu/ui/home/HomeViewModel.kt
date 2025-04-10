package com.ssafy.achu.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.productRepository
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.util.getErrorResponse
import com.ssafy.achu.data.model.product.Category
import com.ssafy.achu.data.model.product.CategoryResponse
import com.ssafy.achu.data.model.product.LikeProduct
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "HomeViewModel_안주현"

class HomeViewModel: ViewModel() {
    private val _likeItemList = MutableStateFlow<List<LikeProduct>>(emptyList())
    val likeItemList: StateFlow<List<LikeProduct>> = _likeItemList

    private val _likeItems = MutableStateFlow<List<Boolean>>(emptyList())
    val likeItems: StateFlow<List<Boolean>> = _likeItems


    // 상태 변수들
    private var currentOffset = 0
    private var hasMoreData = true

    // isLoading 값을 외부에서 관찰할 수 있도록 State로 노출
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadMoreItems() {
        Log.d(TAG, "loadMoreItems: $currentOffset")

        if (_isLoading.value || !hasMoreData) return // 이미 로딩 중이거나 데이터가 없으면 중단

        _isLoading.value = true

        viewModelScope.launch {

            productRepository.getLikedGoods(
                offset = currentOffset,
                limit = 10,
                sort = "LATEST"
            ).onSuccess { result ->
                val newItems = result.data

                if (newItems.isEmpty()) {
                    hasMoreData = false // 더 이상 데이터가 없음을 표시
                } else {
                    val currentList = _likeItemList.value ?: emptyList()
                    val currentLikeList = _likeItems.value ?: emptyList()
                    _likeItemList.value = currentList + newItems
                    _likeItems.value = currentLikeList + List(newItems.size) { true }
                    currentOffset ++
                }
                _isLoading.value = false
            }.onFailure { error ->
                Log.d(TAG, "loadMoreItems error: ${error.getErrorResponse(retrofit)}")
            }

        }
    }



    // 기존의 getLikeItemList() 함수는 초기 로드에만 사용
    fun getLikeItemList() {
        // 초기화
        currentOffset = 0
        hasMoreData = true
        _likeItemList.value = emptyList()

        // 실제 데이터 로드는 loadMoreItems()를 활용
        loadMoreItems()
    }
    fun likeItem(productId: Int, babyId: Int, index: Int){
        viewModelScope.launch {
            productRepository.likeProduct(productId, babyId).onSuccess {
                Log.d(TAG, "likeItem: ${it}")
                _likeItems.value = _likeItems.value.mapIndexed { i, isLiked ->
                    if (i == index) !isLiked else isLiked
                }

            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "likeItem: ${errorResponse}")
            }

        }
    }

    fun unlikeItem(productId: Int, index: Int){
        viewModelScope.launch {
            productRepository.unlikeProduct(productId).onSuccess {
                Log.d(TAG, "unlikeItem: ${it}")
                _likeItems.value = _likeItems.value.mapIndexed { i, isLiked ->
                    if (i == index) !isLiked else isLiked
                }
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "unlikeProduct: ${errorResponse}")
            }

        }
    }


}