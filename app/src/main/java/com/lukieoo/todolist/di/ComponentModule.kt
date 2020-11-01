package com.lukieoo.todolist.di

import android.app.Application
import com.lukieoo.todolist.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.DaggerApplication


@Module
abstract class  ComponentModule {

    @Binds
    abstract fun bindApplication(app: DaggerApplication): Application

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}