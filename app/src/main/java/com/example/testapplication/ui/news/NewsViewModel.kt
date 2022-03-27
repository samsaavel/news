package com.example.testapplication.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.data.ApiResult
import com.example.testapplication.data.NewsResponse
import com.example.testapplication.repository.NewsRepositoryContract
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
    private val keyword = ""

    fun getNewsHeadlines(newCountry: String) {
        viewModelScope.launch {
            _newsState.value = when(val response = newsRepo.getAllNews(newCountry)){
                is ApiResult.Failure -> NewsState.Failure
                is ApiResult.Success -> NewsState.Success(response.data)
            }
        }
    }

    fun getNewsByTopic(newKeyword: String) {
        viewModelScope.launch {
            try {
                if (newKeyword.isNullOrEmpty())
                    newsRepo.getNewsByTopic(keyword)
                else
                    newsRepo.getNewsByTopic(newKeyword)
            } catch (e: Throwable) {
                ApiResult.Failure
            }
        }
    }
}