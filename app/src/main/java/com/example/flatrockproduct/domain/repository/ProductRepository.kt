package com.example.flatrockproduct.domain.repository

import com.example.flatrockproduct.domain.product.Product
import com.example.flatrockproduct.domain.product.ProductDetail
import com.example.flatrockproduct.presentation.util.Resource
import retrofit2.http.Path

interface ProductRepository  {
    suspend fun getAllProduct(): Resource<Product>
    suspend fun getProduct(@Path("id") id: Int): Resource<ProductDetail>
}