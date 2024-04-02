package com.example.flatrockproduct.domain.product

data class ProductDetailsInfo(
    val productDetailsPerProduct: Map<Int, ProductDetail?>,
    val productList: List<Product?>?
)
