package com.grupposts.data.di

import com.grupposts.data.api.ApiService
import com.grupposts.data.api.AuthInterceptor
import com.grupposts.data.utils.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val BASE_URL = "http://healthcarests.metide.com/api/"

    @Provides
    @Singleton
    fun provideHttpClient(sessionManager: SessionManager): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(sessionManager))
        .build()

    @Provides
    @Singleton
    fun provideAPIService(client: OkHttpClient): ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(ApiService::class.java)

}