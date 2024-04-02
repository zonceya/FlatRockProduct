package com.example.flatrockproduct.presentation.util

import com.example.flatrockproduct.domain.product.Product
import com.example.flatrockproduct.domain.product.ProductDetailsInfo

data class ProductState(
    val productDetailsInfo: ProductDetailsInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
