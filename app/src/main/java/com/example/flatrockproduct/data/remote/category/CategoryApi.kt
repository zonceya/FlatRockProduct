package com.example.flatrockproduct.data.remote.category

import com.example.flatrockproduct.data.remote.product.ProductDataDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryApi {
    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): ProductDataDto
}