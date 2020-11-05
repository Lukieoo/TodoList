package com.lukieoo.todolist.di

import android.app.Application
import com.lukieoo.todolist.DaggerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ComponentModule::class,
        FragmentsModule::class,
        FirebaseModule::class,
        AdapterModule::class,
        EventModule::class
    ]
)


interface AppComponent :
    AndroidInjector<DaggerApplication> {
    fun inject(application: Application)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

}