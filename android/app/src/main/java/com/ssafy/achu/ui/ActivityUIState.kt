package com.ssafy.achu.ui

import com.ssafy.achu.data.model.auth.UserInfoResponse
import com.ssafy.achu.data.model.baby.BabyResponse
import com.ssafy.achu.data.model.product.Category
import com.ssafy.achu.data.model.product.ProductDetailResponse
import com.ssafy.achu.data.model.product.Seller

data class ActivityUIState(
    val user: UserInfoResponse? = null,

    val babyList: List<BabyResponse> = emptyList(),
    val selectedBaby: BabyResponse? = null,

    val product: ProductDetailResponse = ProductDetailResponse(
        id = 0,
        title = "",
        description = "",
        imgUrls = emptyList(),
        tradeStatus = "SELLING",
        price = 0,
        createdAt = "",
        likedUsersCount = 0,
        likedByUser = false,
        category = Category(
            id = 0,
            name = "",
            imgUrl = ""
        ),
        seller = Seller(
            id = 0,
            nickname = "",
            imgUrl = ""
        )
    ),
)