package com.technical_challenge.yape.data.retrofit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * In this object, will be created and provided each Repository implemented with Retrofit
 */
@Module
@InstallIn(SingletonComponent::class)
object MockableClient {

    private const val BASE_URL = "http://demo0449138.mockable.io/"

    @Singleton
    @Provides
    fun provideOkHttp() : OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Singleton
    @Provides
    fun provideRepositoriesApiClient(retrofit: Retrofit): MockableRecipeApi {
        return retrofit.create(MockableRecipeApi::class.java)
    }
}