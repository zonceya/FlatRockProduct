package com.example.flatrockproduct.data.repository

import com.example.flatrockproduct.data.mappers.toProduct
import com.example.flatrockproduct.data.mappers.toProductDetail
import com.example.flatrockproduct.data.remote.ProductApi
import com.example.flatrockproduct.domain.product.Product
import com.example.flatrockproduct.domain.product.ProductDetail
import com.example.flatrockproduct.domain.repository.ProductRepository
import com.example.flatrockproduct.presentation.util.Resource

class ProductRepositoryImpl(
    private val api: ProductApi
) : ProductRepository {

    override suspend fun getAllProduct(): Resource<Product> {
        return try {
            val product = api.getProducts().toProduct()
            Resource.Success(product)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getProduct(id: Int): Resource<ProductDetail> {
        return try {
            val productDetail = api.getProductDetail(id).toProductDetail()
            Resource.Success(productDetail)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}
