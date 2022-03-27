package com.example.testapplication.di.module

import com.example.testapplication.network.GeoAPI
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
    private val baseUrlNews = "https://newsapi.org/v2/"
    private val apiKeyNameNews = "apiKey"
    private val apiKeyValueNews = "ef788c8a6fa94fd29a67d2ea0e7f0123"
    private val defaultTime: Long = 15

    private val baseUrlGeo = "http://api.openweathermap.org/"
    private val apiKeyNameGeo = "appid"
    private val apiKeyValueGeo = "249e09d24ba11ef5fb9c69164b1e69bc"


    @Singleton
    @Provides
    @Named("loggingInterceptor")
    fun providesLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    @Named("newsKeyInterceptor")
    fun providesNewsApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val url =
                request.url.newBuilder().addQueryParameter(apiKeyNameNews, apiKeyValueNews).build()
            chain.proceed(request.newBuilder().url(url).build())
        }
    }

    @Singleton
    @Provides
    @Named("geoKeyInterceptor")
    fun providesGeoApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val url =
                request.url.newBuilder().addQueryParameter(apiKeyNameGeo, apiKeyValueGeo).build()
            chain.proceed(request.newBuilder().url(url).build())
        }
    }

    @Singleton
    @Provides
    @Named("newsHttpClient")
    fun provideNewsOkHttpClient(
        @Named("newsKeyInterceptor") newsKeyInterceptor: Interceptor,
        @Named("loggingInterceptor") loggingInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().connectTimeout(defaultTime, TimeUnit.SECONDS)
            .readTimeout(defaultTime, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(newsKeyInterceptor)
            .build()
    }

    @Singleton
    @Provides
    @Named("geoHttpClient")
    fun provideGeoOkHttpClient(
        @Named("geoKeyInterceptor") geoKeyInterceptor: Interceptor,
        @Named("loggingInterceptor") loggingInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().connectTimeout(defaultTime, TimeUnit.SECONDS)
            .readTimeout(defaultTime, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(geoKeyInterceptor)
            .build()
    }

    @Singleton
    @Provides
    @Named("newsRetrofit")
    fun providesNewsRetrofit(
        @Named("newsHttpClient") okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrlNews)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    @Named("geoRetrofit")
    fun providesGeoRetrofit(
        @Named("geoHttpClient") okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrlGeo)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideNewsAPI(
        @Named("newsRetrofit") retrofitNews: Retrofit
    ): NewsAPI = retrofitNews.create(NewsAPI::class.java)

    @Provides
    fun provideGeoAPI(
        @Named("geoRetrofit") retrofitGeo: Retrofit
    ): GeoAPI = retrofitGeo.create(GeoAPI::class.java)

}