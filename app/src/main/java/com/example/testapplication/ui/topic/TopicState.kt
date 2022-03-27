package com.example.testapplication.ui.topic

import com.example.testapplication.data.NewsResponse
import com.example.testapplication.ui.news.NewsState

sealed class TopicState {
    object None : TopicState()
    object Loading : TopicState()
    data class Success(
        val newsResponse: NewsResponse
    ) : TopicState()

    object Failure : TopicState()
}