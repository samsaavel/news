package com.example.testapplication.di.module

import com.example.testapplication.network.NewsAPI
import com.example.testapplication.repository.NewsRepository
import com.example.testapplication.repository.NewsRepositoryContract
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun providesNewsRepository(newsAPI: NewsAPI): NewsRepositoryContract =
        NewsRepository(newsAPI)
}