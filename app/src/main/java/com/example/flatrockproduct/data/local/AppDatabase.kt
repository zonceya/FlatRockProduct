package com.example.flatrockproduct.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.flatrockproduct.data.entities.ProductDetailEntity
import com.example.flatrockproduct.data.entities.ProductTypeConverters

@Database(entities = [ProductDetailEntity::class], version = 1, exportSchema = false)
@TypeConverters(ProductTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDetailDao(): ProductDao
}
