package com.ssafy.achu.data.network

import com.ssafy.achu.data.model.ApiResult
import com.ssafy.achu.data.model.product.CategoryResponse
import com.ssafy.achu.data.model.product.ProductDetailResponse
import com.ssafy.achu.data.model.product.ProductResponse
import retrofit2.http.GET
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
        @Path("goodsId") productId: Int
    ): ApiResult<ProductDetailResponse>
}