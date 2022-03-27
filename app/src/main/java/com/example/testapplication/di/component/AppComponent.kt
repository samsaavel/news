package com.example.testapplication.di.component

import android.app.Application
import com.example.testapplication.ProjectApplication
import com.example.testapplication.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ActivityBuilderModule::class,
        NetworkModule::class,
        FragmentBuilderModule::class,
        AndroidSupportInjectionModule::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent : AndroidInjector<ProjectApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}