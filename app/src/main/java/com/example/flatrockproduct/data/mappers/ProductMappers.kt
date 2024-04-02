package com.example.flatrockproduct.data.mappers

import com.example.flatrockproduct.data.remote.ProductDTO
import com.example.flatrockproduct.data.remote.ProductDataDto
import com.example.flatrockproduct.domain.product.Product
import com.example.flatrockproduct.domain.product.ProductDetail


fun ProductDTO.toProduct(): Product? {
    return products.map { it.toProductDetail() }?.let {
        Product(
        limit = limit,
        skip = skip,
        total = total,
        products = it
    )
    }
}


fun ProductDataDto.toProductDetail(): ProductDetail? {
    return  ProductDetail(
            id = id,
            title = title,
            description = description,
            price = price.toDouble(),
            discountPercentage = discountPercentage ?: 0.0,
            rating = rating ?: 0.0,
            stock = stock,
            brand = brand,
            category = category,
            thumbnail = thumbnail,
            images = images
        )
    }



