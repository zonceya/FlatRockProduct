package com.example.flatrockproduct.data.remote.category

data class test(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)