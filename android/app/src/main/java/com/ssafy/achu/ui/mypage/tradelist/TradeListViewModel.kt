package com.ssafy.achu.ui.mypage.tradelist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.productRepository
import com.ssafy.achu.data.model.product.LikeProduct
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val TAG = "TradeListViewModel_안주현"

class TradeListViewModel : ViewModel() {

    private val _purchaseList = MutableStateFlow<List<LikeProduct>>(emptyList())
    val purchaseList: StateFlow<List<LikeProduct>> = _purchaseList

    private val _saleList = MutableStateFlow<List<LikeProduct>>(emptyList())
    val saleList: StateFlow<List<LikeProduct>> = _saleList


    fun getPurchaseList() {
        viewModelScope.launch {
            productRepository.getTradeList("PURCHASE").onSuccess {
                _purchaseList.value = it.data
                Log.d(TAG, "getPurchaseList: ${it.data}")
            }.onFailure {
                Log.d(TAG, "getPurchaseList: ${it}")
            }
        }
    }

    fun getSaleList() {
        viewModelScope.launch {
            productRepository.getTradeList("SALE").onSuccess {
                _saleList.value = it.data
                Log.d(TAG, "getSaleList: ${it.data}")

            }.onFailure {
                Log.d(TAG, "getSaleList: ${it}")
            }

        }
    }

}