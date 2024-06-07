package com.example.flatrockproduct.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.flatrockproduct.data.local.AppDatabase
import com.example.flatrockproduct.data.local.ProductDao
import com.example.flatrockproduct.data.remote.product.ProductApi
import com.example.flatrockproduct.data.repository.ProductRepositoryImpl
import com.example.flatrockproduct.domain.repository.ProductRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://dummyjson.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()
    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app.applicationContext
    @Provides
    fun provideProductApi(retrofit: Retrofit): ProductApi = retrofit.create(ProductApi::class.java)

    @Singleton
    @Provides
    fun provideDatabase(app: Context): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "product_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideProductDao(db: AppDatabase): ProductDao = db.productDetailDao()

    @Singleton
    @Provides
    fun provideProductRepository(api: ProductApi, dao: ProductDao,  @ApplicationContext context: Context): ProductRepository {
        return ProductRepositoryImpl(api, dao,context)
    }
}
