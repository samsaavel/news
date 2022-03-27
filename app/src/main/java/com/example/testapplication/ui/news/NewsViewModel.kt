package com.example.testapplication.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.data.ApiResult
import com.example.testapplication.repository.NewsRepositoryContract
import com.example.testapplication.ui.topic.TopicState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val newsRepo: NewsRepositoryContract
) : ViewModel() {
    private var _newsState: MutableStateFlow<NewsState> =
        MutableStateFlow(NewsState.None)
    val newsState: StateFlow<NewsState> by lazy { _newsState }
    private var _topicState: MutableStateFlow<TopicState> =
        MutableStateFlow(TopicState.None)
    val topicState: StateFlow<TopicState> by lazy { _topicState }
    private val keyword = ""

    fun getNewsHeadlines(newCountry: String) {
        viewModelScope.launch {
            _newsState.value = when (val response = newsRepo.getAllNews(newCountry)) {
                is ApiResult.Failure -> NewsState.Failure
                is ApiResult.Success -> NewsState.Success(response.data)
            }
        }
    }

    fun getNewsByTopic(newKeyword: String) {
        viewModelScope.launch {
            _topicState.value = when (val response = newsRepo.getNewsByTopic(newKeyword)) {
                is ApiResult.Failure -> TopicState.Failure
                is ApiResult.Success -> TopicState.Success(response.data)
            }
        }
    }
}