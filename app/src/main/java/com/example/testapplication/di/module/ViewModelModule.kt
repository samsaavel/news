package com.example.testapplication.di.module

import androidx.lifecycle.ViewModel
import com.example.testapplication.di.viewmodel.ViewModelKey
import com.example.testapplication.ui.news.NewsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    internal abstract fun bindNewsViewModel(viewModel: NewsViewModel): ViewModel
}