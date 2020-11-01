package com.lukieoo.todolist.di

import com.lukieoo.todolist.DaggerApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ComponentModule::class,
        FragmentsModule::class
    ]
)


interface AppComponent : AndroidInjector<DaggerApplication> {

}