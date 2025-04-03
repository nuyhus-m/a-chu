package com.ssafy.achu.data.repository

import com.ssafy.achu.data.model.ApiResult
import com.ssafy.achu.data.model.IdResponse
import com.ssafy.achu.data.model.product.CategoryResponse
import com.ssafy.achu.data.model.product.LikeProduct
import com.ssafy.achu.data.model.product.ModifyProductRequest
import com.ssafy.achu.data.model.product.ProductDetailResponse
import com.ssafy.achu.data.model.product.ProductResponse
import com.ssafy.achu.data.model.product.UploadProductRequest
import com.ssafy.achu.data.network.RetrofitUtil
import okhttp3.MultipartBody

class ProductRepository {

    private val productService = RetrofitUtil.productService

    suspend fun getCategoryList(): Result<ApiResult<List<CategoryResponse>>> {
        return runCatching {
            productService.getCategoryList()
        }
    }

    suspend fun getProductListByCategory(
        categoryId: Int,
        offset: Int,
        limit: Int,
        sort: String
    ): Result<ApiResult<List<ProductResponse>>> {
        return runCatching {
            productService.getProductListByCategory(
                categoryId = categoryId,
                offset = offset,
                limit = limit,
                sort = sort
            )
        }
    }

    suspend fun getProductList(
        offset: Int,
        limit: Int,
        sort: String
    ): Result<ApiResult<List<ProductResponse>>> {
        return runCatching {
            productService.getProductList(
                offset = offset,
                limit = limit,
                sort = sort
            )
        }
    }

    suspend fun searchProduct(
        keyword: String,
        offset: Int,
        limit: Int,
        sort: String
    ): Result<ApiResult<List<ProductResponse>>> {
        return runCatching {
            productService.searchProduct(
                keyword = keyword,
                offset = offset,
                limit = limit,
                sort = sort
            )
        }
    }

    suspend fun searchProductByCategory(
        categoryId: Int,
        keyword: String,
        offset: Int,
        limit: Int,
        sort: String
    ): Result<ApiResult<List<ProductResponse>>> {
        return runCatching {
            productService.searchProductByCategory(
                categoryId = categoryId,
                keyword = keyword,
                offset = offset,
                limit = limit,
                sort = sort
            )
        }
    }

    suspend fun getProductDetail(
        productId: Int
    ): Result<ApiResult<ProductDetailResponse>> {
        return runCatching {
            productService.getProductDetail(productId)
        }
    }

    suspend fun deleteProduct(
        productId: Int
    ): Result<ApiResult<IdResponse>> {
        return runCatching {
            productService.deleteProduct(productId)
        }
    }

    suspend fun likeProduct(
        productId: Int
    ): Result<ApiResult<Unit>> {
        return runCatching {
            productService.likeProduct(productId)
        }
    }

    suspend fun unlikeProduct(
        productId: Int
    ): Result<ApiResult<Unit>> {
        return runCatching {
            productService.unlikeProduct(productId)
        }
    }

    suspend fun getLikedGoods(
        offset: Int = 0,
        limit: Int = 20,
        sort: String = "LATEST"
    ): Result<ApiResult<List<LikeProduct>>> {
        return runCatching {
            productService.getLikedGoods(offset, limit, sort)
        }
    }

    suspend fun getTradeList(
        tradeType: String,
        offset: Int = 0,
        limit: Int = 20,
        sort: String = "LATEST"
    ): Result<ApiResult<List<LikeProduct>>> {
        return runCatching {
            productService.getTrades(tradeType, offset, limit, sort)
        }
    }

    suspend fun uploadProduct(
        images: List<MultipartBody.Part>,
        request: UploadProductRequest
    ): Result<ApiResult<IdResponse>> {
        return runCatching {
            productService.uploadProduct(images, request)
        }
    }

    suspend fun modifyProduct(
        productId: Int,
        request: ModifyProductRequest
    ): Result<ApiResult<Unit>> {
        return runCatching {
            productService.modifyProduct(productId, request)
        }
    }

}