package com.ssafy.achu.ui.mypage.likelist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.productRepository
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.util.getErrorResponse
import com.ssafy.achu.data.model.product.LikeProduct
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val TAG = "LikeItemListViewModel_안주현"
class LikeItemListViewModel: ViewModel() {

    private val _likeItemList = MutableStateFlow<List<LikeProduct>>(emptyList())
    val likeItemList: StateFlow<List<LikeProduct>> = _likeItemList

    fun getLikeItemList() {
        viewModelScope.launch {
            productRepository.getLikedGoods(
                offset = 0,
                limit = 20,
                sort = "LATEST"
            ).onSuccess { result ->
                val currentList = _likeItemList.value ?: emptyList()
                _likeItemList.value = currentList + result.data
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "getLikeItemList:  ${errorResponse}")
            }
        }
    }

    fun likeItem(productId: Int){
        viewModelScope.launch {
            productRepository.likeProduct(productId).onSuccess {
                Log.d(TAG, "likeItem: ${it}")
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "likeItem: ${errorResponse}")
            }

        }
    }

    fun unlikeItem(productId: Int){
        viewModelScope.launch {
            productRepository.unlikeProduct(productId).onSuccess {
                Log.d(TAG, "unlikeItem: ${it}")
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "unlikeProduct: ${errorResponse}")
            }

        }
    }

}