package com.example.testapplication.di.module

import com.example.testapplication.network.GeoAPI
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
class GeoNetworkModule {
    private val baseUrl = "http://api.openweathermap.org/"
    private val apiKeyName = "appid"
    private val apiKeyValue = "249e09d24ba11ef5fb9c69164b1e69bc"
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
    fun provideGeoAPI(retrofit: Retrofit): GeoAPI = retrofit.create(GeoAPI::class.java)
}