package com.example.flatrockproduct.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "product_details")
@TypeConverters(ProductTypeConverters::class)
data class ProductDetailEntity(
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
{
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}