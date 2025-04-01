package com.ssafy.achu.data.repository

import com.ssafy.achu.data.model.ApiResult
import com.ssafy.achu.data.model.product.CategoryResponse
import com.ssafy.achu.data.model.product.ProductDetailResponse
import com.ssafy.achu.data.model.product.ProductResponse
import com.ssafy.achu.data.network.RetrofitUtil

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
}