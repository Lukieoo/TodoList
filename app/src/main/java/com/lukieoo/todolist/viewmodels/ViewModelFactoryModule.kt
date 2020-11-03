package com.anioncode.gamevideodagger.viewmodels

import androidx.lifecycle.ViewModelProvider
import com.lukieoo.todolist.viewmodels.ViewModelProviderFactory
import dagger.Binds
import dagger.Module


@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelProviderFactory(viewModelFactory: ViewModelProviderFactory): ViewModelProvider.Factory

}