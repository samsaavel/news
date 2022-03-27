package com.example.testapplication.repository

import com.example.testapplication.data.ApiResult
import com.example.testapplication.data.NewsResponse
import com.example.testapplication.network.NewsAPI
import com.example.testapplication.utils.ifEmptySubstituteTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface NewsRepositoryContract {
    suspend fun getAllNews(country: String): ApiResult<NewsResponse>
    suspend fun getNewsByTopic(keyword: String): ApiResult<NewsResponse>
}

class NewsRepository @Inject constructor(private val newsAPI: NewsAPI) :
    NewsRepositoryContract {
    internal val defaultCountry = "us"

    override suspend fun getNewsByTopic(keyword: String): ApiResult<NewsResponse> {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext ApiResult.Success(newsAPI.getNewsByTopic(keyword))
            } catch (e: Throwable) {
                return@withContext ApiResult.Failure
            }
        }
    }

    override suspend fun getAllNews(country: String): ApiResult<NewsResponse> {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext ApiResult.Success(
                    newsAPI.getAllHeadlines(country.ifEmptySubstituteTo(defaultCountry))
                )
            } catch (e: Throwable) {
                return@withContext ApiResult.Failure
            }
        }
    }
}