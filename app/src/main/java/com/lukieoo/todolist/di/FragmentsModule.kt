package com.lukieoo.todolist.di

import com.lukieoo.todolist.navigation.FragmentBindingModule
import com.lukieoo.todolist.fragments.MainNavHostFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class  FragmentsModule {


    @ContributesAndroidInjector(modules = [FragmentBindingModule::class])
    abstract fun contributeMainNavHostFragment(): MainNavHostFragment

}