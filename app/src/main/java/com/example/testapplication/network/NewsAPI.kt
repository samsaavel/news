package com.example.testapplication.network

import com.example.testapplication.data.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("top-headlines")
    suspend fun getAllHeadlines(
        @Query("country") country: String
    ): NewsResponse

    @GET("everything")
    suspend fun getNewsByTopic(
        @Query("q") keyword: String
    ): NewsResponse
}