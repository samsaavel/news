package com.example.testapplication.di.module

import com.example.testapplication.network.NewsAPI
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {
    private val baseUrl = "https://newsapi.org/v2/"
    private val apiKeyName = "apiKey"
    private val apiKeyValue = "ef788c8a6fa94fd29a67d2ea0e7f0123"
    private val defaultTime: Long = 15


    @Singleton
    @Provides
    @Named("loggingInterceptor")
    fun providesLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    @Named("keyInterceptor")
    fun providesApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val url = request.url.newBuilder().addQueryParameter(apiKeyName, apiKeyValue).build()
            chain.proceed(request.newBuilder().url(url).build())
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @Named("keyInterceptor") apiKeyInterceptor: Interceptor,
        @Named("loggingInterceptor") loggingInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().connectTimeout(defaultTime, TimeUnit.SECONDS)
            .readTimeout(defaultTime, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideNewsAPI(retrofit: Retrofit): NewsAPI = retrofit.create(NewsAPI::class.java)
}