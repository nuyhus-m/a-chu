package com.ssafy.achu.data.network

import com.ssafy.achu.core.ApplicationClass

class RetrofitUtil {
    companion object {
        val authService: AuthService = ApplicationClass.retrofit.create(AuthService::class.java)
        val userService: UserService = ApplicationClass.retrofit.create(UserService::class.java)
        val productService: ProductService = ApplicationClass.retrofit.create(ProductService::class.java)
        val memoryService: MemoryService = ApplicationClass.retrofit.create(MemoryService::class.java)
        val babyService: BabyService = ApplicationClass.retrofit.create(BabyService::class.java)
    }
}