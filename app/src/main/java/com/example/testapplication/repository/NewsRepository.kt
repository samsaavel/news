package com.example.testapplication.repository

import android.util.Log
import com.example.testapplication.data.ApiResult
import com.example.testapplication.data.GeocodingResponse
import com.example.testapplication.data.NewsResponse
import com.example.testapplication.network.GeoAPI
import com.example.testapplication.network.NewsAPI
import com.example.testapplication.utils.ifEmptySubstituteTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface NewsRepositoryContract {
    suspend fun getNewsFromAPI(country: String): ApiResult<NewsResponse>
    suspend fun getNewsByTopic(keyword: String): ApiResult<NewsResponse>
}

class NewsRepository @Inject constructor(private val newsAPI: NewsAPI, private val geoAPI: GeoAPI) :
    NewsRepositoryContract {
    internal val defaultCity = "Atlanta"
    internal val defaultCountry = "us"
    internal val defaultTopic = "rusia"

    override suspend fun getNewsFromAPI(country: String): ApiResult<NewsResponse> {
        val wrappedGeocoding = getCountryCode(country.ifEmptySubstituteTo(defaultCity))
        Log.d("wrappedGeo", wrappedGeocoding.toString())
        return when (wrappedGeocoding) {
            is ApiResult.Failure -> ApiResult.Failure
            is ApiResult.Success -> getAllNews(wrappedGeocoding.data[0].country)
        }
    }


    override suspend fun getNewsByTopic(keyword: String): ApiResult<NewsResponse> {
        return withContext(Dispatchers.IO) {
            try {
                ApiResult.Success(newsAPI.getNewsByTopic(keyword.ifEmptySubstituteTo(defaultTopic)))
            } catch (e: Throwable) {
                ApiResult.Failure
            }
        }
    }

    private suspend fun getAllNews(country: String): ApiResult<NewsResponse> {
        return withContext(Dispatchers.IO) {
            try {
                ApiResult.Success(newsAPI.getAllHeadlines(country.ifEmptySubstituteTo(defaultCountry)))
            } catch (e: Throwable) {
                ApiResult.Failure
            }
        }
    }

    private suspend fun getCountryCode(cityOrCountry: String): ApiResult<List<GeocodingResponse>> {
        return withContext(Dispatchers.IO) {
            try {
                val city = cityOrCountry.ifEmptySubstituteTo(defaultCity)
                ApiResult.Success(geoAPI.getCodeCountry(city))
            } catch (e: Throwable) {
                ApiResult.Failure
            }
        }
    }
}