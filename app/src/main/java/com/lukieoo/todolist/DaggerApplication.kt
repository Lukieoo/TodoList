package com.lukieoo.todolist

import android.app.Application
import com.lukieoo.todolist.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class DaggerApplication: Application(), HasAndroidInjector {


    @Inject
    lateinit var androidInjector:DispatchingAndroidInjector<Any> //in Java Object

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder().application(this)!!.build().inject(this)
    }

    override fun androidInjector()=androidInjector

}
