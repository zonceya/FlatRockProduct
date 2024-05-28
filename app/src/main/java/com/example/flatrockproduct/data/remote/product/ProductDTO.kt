package com.example.flatrockproduct.data.remote.product

import com.example.flatrockproduct.data.remote.product.ProductDataDto

data class ProductDTO(
    val products: List<ProductDataDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

