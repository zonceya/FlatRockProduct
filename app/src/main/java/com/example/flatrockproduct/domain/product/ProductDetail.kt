package com.example.flatrockproduct.domain.product

data class ProductDetail(
    val id: Int,
    val brand: String?,
    val category: String,
    val description: String,
    val discountPercentage: Double,
    val images: List<String>,
    val price: Double,
    val rating: Double,
    val stock: Int,
    val thumbnail: String,
    val title: String
)
