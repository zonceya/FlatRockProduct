package com.example.flatrockproduct.di

import com.example.flatrockproduct.data.remote.ProductApi
import com.example.flatrockproduct.data.repository.ProductRepositoryImpl
import com.example.flatrockproduct.domain.repository.ProductRepository
import com.example.flatrockproduct.presentation.util.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://dummyjson.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideProduct(retrofit: Retrofit): ProductApi = retrofit.create(ProductApi::class.java)
    @Provides
    fun provideProductRepository(api: ProductApi): ProductRepository {
        return ProductRepositoryImpl(api)
    }

}