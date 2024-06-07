package com.example.flatrockproduct.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flatrockproduct.data.entities.ProductDetailEntity
import com.example.flatrockproduct.domain.product.ProductDetail

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductDetailEntity>)

    @Query("SELECT * FROM product_details WHERE id = :id")
    suspend fun getProductById(id: Int): ProductDetailEntity?

    @Query("SELECT * FROM product_details")
    suspend fun getAllProducts(): List<ProductDetailEntity>
}
