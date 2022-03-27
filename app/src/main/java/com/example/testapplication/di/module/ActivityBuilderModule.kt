package com.example.testapplication.di.module

import com.example.testapplication.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(
        modules = [
            FragmentBuilderModule::class,
            ViewModelFactoryModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}