package com.example.testapplication.di.module

import com.example.testapplication.ui.news.NewsFragment
import com.example.testapplication.ui.topic.SearchTopicFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun providesNewsFragment(): NewsFragment

    @ContributesAndroidInjector
    abstract fun providesSearchTopicFragment(): SearchTopicFragment
}
