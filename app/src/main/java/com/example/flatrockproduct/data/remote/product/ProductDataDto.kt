package com.example.flatrockproduct.data.remote.product


data class ProductDataDto(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String?,
    val category: String,
    val thumbnail: String,
    val images: List<String>
)
