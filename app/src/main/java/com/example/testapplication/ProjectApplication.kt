package com.example.testapplication

import com.example.testapplication.di.component.DaggerAppComponent
import dagger.android.DaggerApplication

class ProjectApplication : DaggerApplication() {
    override fun applicationInjector() = DaggerAppComponent.factory().create(this)
}