package com.example.flatrockproduct.data.repository


import android.content.Context
import com.example.flatrockproduct.data.local.ProductDao
import com.example.flatrockproduct.data.mappers.toDomain
import com.example.flatrockproduct.data.mappers.toEntity
import com.example.flatrockproduct.data.remote.product.ProductApi
import com.example.flatrockproduct.domain.product.Product
import com.example.flatrockproduct.domain.product.ProductDetail
import com.example.flatrockproduct.domain.repository.ProductRepository
import com.example.flatrockproduct.presentation.util.NetworkUtil
import com.example.flatrockproduct.presentation.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
 class ProductRepositoryImpl(
    private val api: ProductApi,
    private val productDao: ProductDao,
    private val context: Context
) : ProductRepository {

     override suspend fun getAllProduct(): Resource<Product> {
         return if (NetworkUtil.isNetworkAvailable(context)) {
             try {
                 val productDTO = api.getProducts()
                 val productEntities = productDTO.products.map { it.toDomain().toEntity() }
                 withContext(Dispatchers.IO) {
                     productDao.insertProducts(productEntities)
                 }
                 Resource.Success(productDTO.toDomain())
             } catch (e: Exception) {
                 Resource.Error(e.message ?: "An unknown error occurred.")
             }
         } else {
             try {
                 val productEntities = productDao.getAllProducts()
                 val products = productEntities.map { it.toDomain() }
                 Resource.Success(Product(
                     limit = 0, // Set appropriate values
                     skip = 0,
                     total = products.size,
                     products = products
                 ))
             } catch (e: Exception) {
                 Resource.Error(e.message ?: "An unknown error occurred while loading cached data.")
             }
         }
     }


     override suspend fun getProduct(id: Int): Resource<ProductDetail> {
         return if (NetworkUtil.isNetworkAvailable(context)) {
             try {
                 val productDetailDto = api.getProductDetail(id)
                 val productDetailEntity = productDetailDto.toDomain().toEntity()
                 withContext(Dispatchers.IO) {
                     productDao.insertProducts(listOf(productDetailEntity))
                 }
                 Resource.Success(productDetailEntity.toDomain())
             } catch (e: Exception) {
                 Resource.Error(e.message ?: "An unknown error occurred.")
             }
         } else {
             try {
                 val productDetailEntity = productDao.getProductById(id)
                 val productDetail = productDetailEntity?.toDomain()
                 if (productDetail != null) {
                     Resource.Success(productDetail)
                 } else {
                     Resource.Error("Product not found")
                 }
             } catch (e: Exception) {
                 Resource.Error(e.message ?: "An unknown error occurred while loading cached data.")
             }
         }
     }

     override suspend fun getCategory(category: String): Resource<ProductDetail> {
         TODO("Not yet implemented")
     }
 }



     /*override suspend fun getProductsByCategory(category: String): Resource<Product> {
         return try {
             val categoryProducts = api.getProductsByCategory(category).toCategory()
             Resource.Success(categoryProducts)
         } catch (e: Exception) {
             Resource.Error(e.message ?: "An unknown error occurred.")
         }
     }*/

