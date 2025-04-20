package com.ssafy.achu.ui.mypage.tradelist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.productRepository
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.util.getErrorResponse
import com.ssafy.achu.data.model.product.LikeProduct
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.plus

private const val TAG = "TradeListViewModel_안주현"

class TradeListViewModel : ViewModel() {

    private val _purchaseList = MutableStateFlow<List<LikeProduct>>(emptyList())
    val purchaseList: StateFlow<List<LikeProduct>> = _purchaseList

    private val _saleList = MutableStateFlow<List<LikeProduct>>(emptyList())
    val saleList: StateFlow<List<LikeProduct>> = _saleList


    // 상태 변수들
    private var purchaseCurrentOffset = 0
    private var hasMorePurchaseData = true

    // isLoading 값을 외부에서 관찰할 수 있도록 State로 노출
    private val _isPurchaseLoading = MutableStateFlow(false)
    val isPurchaseLoading: StateFlow<Boolean> = _isPurchaseLoading.asStateFlow()


    // 더 많은 아이템을 로드하는 함수
    fun loadMorePurchaseItems() {
        Log.d(TAG, "loadMoreItems: ${purchaseCurrentOffset}")
        if (_isPurchaseLoading.value || !hasMorePurchaseData) return

        _isPurchaseLoading.value = true

        viewModelScope.launch {

            productRepository.getTradeList(
                tradeType = "PURCHASE",
                offset = purchaseCurrentOffset,
                limit = 10,
                sort = "LATEST"
            ).onSuccess { result ->
                val newItems = result.data

                // 새 아이템이 없으면 더 이상 데이터가 없는 것으로 판단
                if (newItems.isEmpty()) {
                    hasMorePurchaseData = false
                } else {
                    val currentList = _purchaseList.value ?: emptyList()
                    _purchaseList.value = currentList + newItems

                    // 다음 페이지를 위해 오프셋 업데이트
                    purchaseCurrentOffset++
                }

                _isPurchaseLoading.value = false

            }.onFailure { error ->
                val errorResponse = error.getErrorResponse(retrofit)
                Log.d(TAG, "getLikeItemList: ${errorResponse}")
            }
        }
    }

    fun getPurchaseList() {
        // 초기화
        purchaseCurrentOffset = 0
        hasMorePurchaseData = true
        _purchaseList.value = emptyList()

        loadMorePurchaseItems()
    }



    // 상태 변수들
    private var saleCurrentOffset = 0
    private var hasMoreSaleData = true

    // isLoading 값을 외부에서 관찰할 수 있도록 State로 노출
    private val _isSaleLoading = MutableStateFlow(false)
    val isSaleLoading: StateFlow<Boolean> = _isSaleLoading.asStateFlow()


    // 더 많은 아이템을 로드하는 함수
    fun loadMoreSaleItems() {
        Log.d(TAG, "loadMoreItems: ${saleCurrentOffset}")
        if (_isSaleLoading.value || !hasMoreSaleData) return

        _isSaleLoading.value = true

        viewModelScope.launch {

            productRepository.getTradeList(
                tradeType = "SALE",
                offset = saleCurrentOffset,
                limit = 10,
                sort = "LATEST"
            ).onSuccess { result ->
                val newItems = result.data

                // 새 아이템이 없으면 더 이상 데이터가 없는 것으로 판단
                if (newItems.isEmpty()) {
                    hasMorePurchaseData = false
                } else {
                    val currentList = _saleList.value ?: emptyList()
                    _saleList.value = currentList + newItems

                    // 다음 페이지를 위해 오프셋 업데이트
                    saleCurrentOffset++
                }

                _isSaleLoading.value = false

            }.onFailure { error ->
                val errorResponse = error.getErrorResponse(retrofit)
                Log.d(TAG, "getLikeItemList: ${errorResponse}")
            }
        }
    }

    fun getSaleList() {
        saleCurrentOffset = 0
        hasMoreSaleData = true
        _saleList.value = emptyList()
        loadMoreSaleItems()
    }






}