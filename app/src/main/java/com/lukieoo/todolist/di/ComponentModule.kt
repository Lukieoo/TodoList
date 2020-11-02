package com.lukieoo.todolist.di

import android.app.Application
import com.lukieoo.todolist.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.DaggerApplication


@Module
abstract class  ComponentModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}