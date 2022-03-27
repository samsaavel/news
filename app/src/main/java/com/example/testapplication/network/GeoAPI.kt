package com.example.testapplication.network

import com.example.testapplication.data.GeocodingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoAPI {
    @GET("geo/1.0/direct")
    suspend fun getCodeCountry(
        @Query("q") cityOrCountry: String
    ): GeocodingResponse
}