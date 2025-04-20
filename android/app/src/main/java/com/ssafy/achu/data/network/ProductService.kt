package com.ssafy.achu.data.network

import com.ssafy.achu.data.model.ApiResult
import com.ssafy.achu.data.model.IdResponse
import com.ssafy.achu.data.model.product.BuyerIdRequest
import com.ssafy.achu.data.model.product.CategoryResponse
import com.ssafy.achu.data.model.product.LikeProduct
import com.ssafy.achu.data.model.product.ModifyProductRequest
import com.ssafy.achu.data.model.product.ProductDetailResponse
import com.ssafy.achu.data.model.product.ProductRequestBabyId
import com.ssafy.achu.data.model.product.ProductResponse
import com.ssafy.achu.data.model.product.UploadProductRequest
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {

    @GET("/categories")
    suspend fun getCategoryList(): ApiResult<List<CategoryResponse>>

    @GET(" /categories/{categoryId}/goods")
    suspend fun getProductListByCategory(
        @Path("categoryId") categoryId: Int,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("sort") sort: String
    ): ApiResult<List<ProductResponse>>

    @GET("/goods")
    suspend fun getProductList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("sort") sort: String
    ): ApiResult<List<ProductResponse>>

    @GET("/goods/search")
    suspend fun searchProduct(
        @Query("keyword") keyword: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("sort") sort: String
    ): ApiResult<List<ProductResponse>>

    @GET("/categories/{categoryId}/goods/search")
    suspend fun searchProductByCategory(
        @Path("categoryId") categoryId: Int,
        @Query("keyword") keyword: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("sort") sort: String
    ): ApiResult<List<ProductResponse>>

    @GET("/goods/{goodsId}")
    suspend fun getProductDetail(
        @Path("goodsId") productId: Int, @Query("babyId") babyId: Int

    ): ApiResult<ProductDetailResponse>

    @DELETE("/goods/{goodsId}")
    suspend fun deleteProduct(
        @Path("goodsId") productId: Int
    ): ApiResult<IdResponse>

    @POST("/goods/{goodsId}/like")
    suspend fun likeProduct(
        @Path("goodsId") productId: Int,
        @Body request: ProductRequestBabyId
    ): ApiResult<Unit>

    @DELETE("/goods/{goodsId}/like")
    suspend fun unlikeProduct(
        @Path("goodsId") productId: Int
    ): ApiResult<Unit>

    @GET("/goods/liked")
    suspend fun getLikedGoods(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("sort") sort: String
    ): ApiResult<List<LikeProduct>>

    @GET("/trades")
    suspend fun getTrades(
        @Query("tradeType") tradeType: String, // "PURCHASE" 또는 "SALE"
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("sort") sort: String // "LATEST" 또는 "OLDEST"
    ): ApiResult<List<LikeProduct>>

    @Multipart
    @POST("/goods")
    suspend fun uploadProduct(
        @Part images: List<MultipartBody.Part>,
        @Part("request") request: UploadProductRequest
    ): ApiResult<IdResponse>

    @PUT("/goods/{goodsId}")
    suspend fun modifyProduct(
        @Path("goodsId") productId: Int,
        @Body request: ModifyProductRequest
    ): ApiResult<Unit>

    @POST("/goods/{goodsId}/trade/complete")
    suspend fun completeTrade(
        @Path("goodsId") productId: Int,
        @Body request: BuyerIdRequest
    ): ApiResult<IdResponse>


    @GET("/babies/{id}/recommend")
    suspend fun getRecommendedItems(
        @Path("id") babyId: Int
    ): ApiResult<List<ProductResponse>>
}

