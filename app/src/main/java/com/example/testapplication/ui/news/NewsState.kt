package com.example.testapplication.ui.news

import com.example.testapplication.data.NewsResponse

sealed class NewsState {
    object None : NewsState()
    object Loading : NewsState()
    data class Success(
        val newsResponse: NewsResponse
    ) : NewsState()

    object Failure : NewsState()
}