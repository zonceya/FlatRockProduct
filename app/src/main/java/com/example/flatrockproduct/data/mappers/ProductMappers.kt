package com.example.flatrockproduct.data.mappers

import com.example.flatrockproduct.data.entities.ProductDetailEntity
import com.example.flatrockproduct.data.remote.product.ProductDTO
import com.example.flatrockproduct.data.remote.product.ProductDataDto
import com.example.flatrockproduct.domain.product.Product
import com.example.flatrockproduct.domain.product.ProductDetail

fun ProductDTO.toDomain(): Product {
    return Product(
        limit = limit,
        skip = skip,
        total = total,
        products = products.map { it.toDomain() }
    )
}

fun ProductDataDto.toDomain(): ProductDetail {
    return ProductDetail(
        id = id,
        title = title,
        description = description,
        price = price,
        discountPercentage = discountPercentage,
        rating = rating,
        stock = stock,
        brand = brand,
        category = category,
        thumbnail = thumbnail,
        images = images
    )
}

fun ProductDetail.toEntity(): ProductDetailEntity {
    return ProductDetailEntity(
        brand = brand,
        category = category,
        description = description,
        discountPercentage = discountPercentage,
        images = images,
        price = price,
        rating = rating,
        stock = stock,
        thumbnail = thumbnail,
        title = title
    ).apply {
        id = this@toEntity.id  // Optional
    }
}
fun ProductDetailEntity.toDomain(): ProductDetail {
    return ProductDetail(
        id = id ?: 0,
        brand = brand,
        category = category,
        description = description,
        discountPercentage = discountPercentage,
        images = images,
        price = price,
        rating = rating,
        stock = stock,
        thumbnail = thumbnail,
        title = title
    )
}
