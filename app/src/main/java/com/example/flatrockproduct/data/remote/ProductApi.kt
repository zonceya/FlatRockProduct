package com.example.flatrockproduct.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {
    @GET("products")
    suspend fun getProducts(): ProductDTO

    @GET("products/{productId}")
    suspend fun getProductDetail(@Path("productId") productId: Int): ProductDataDto
}
