package com.example.flatrockproduct.data.remote.product

import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {
    @GET("products")
    suspend fun getProducts(): ProductDTO
    @GET("products/{productId}")
    suspend fun getProductDetail(@Path("productId") productId: Int): ProductDataDto
    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): ProductDataDto
}
