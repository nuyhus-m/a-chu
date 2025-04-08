package com.ssafy.achu.ui

import android.net.Uri
import com.ssafy.achu.data.model.auth.UserInfoResponse
import com.ssafy.achu.data.model.baby.BabyResponse
import com.ssafy.achu.data.model.chat.Partner
import com.ssafy.achu.data.model.product.CategoryResponse
import com.ssafy.achu.data.model.product.ProductDetailResponse
import com.ssafy.achu.data.model.product.Seller
import com.ssafy.achu.data.model.product.UploadProductRequest
import okhttp3.MultipartBody

data class ActivityUIState(
    val user: UserInfoResponse? = null,

    val babyList: List<BabyResponse> = emptyList(),
    val selectedBaby: BabyResponse? = null,

    val product: ProductDetailResponse = ProductDetailResponse(
        id = 0,
        title = "",
        description = "",
        imgUrls = emptyList(),
        tradeStatus = "",
        price = 0,
        createdAt = "",
        likedUsersCount = 0,
        likedByUser = false,
        category = CategoryResponse(
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
    val previewImgUris: List<Uri> = emptyList(),

    val uploadProductRequest: UploadProductRequest? = null,
    val multiPartImages: List<MultipartBody.Part> = emptyList(),
    val uploadBabyName: String = "",

    val showCreateDialog: Boolean = false,
)