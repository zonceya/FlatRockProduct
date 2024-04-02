package com.example.flatrockproduct.data.remote

data class ProductDTO(
    val products: List<ProductDataDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

