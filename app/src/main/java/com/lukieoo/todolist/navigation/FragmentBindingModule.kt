package com.lukieoo.todolist.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.lukieoo.todolist.fragments.AddFragment
import com.lukieoo.todolist.fragments.DataFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FragmentBindingModule {

    @Binds
    @IntoMap
    @FragmentKey(AddFragment::class)
    abstract fun bindAddFragment(addFragment: AddFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(DataFragment::class)
    abstract fun bindDataFragment(dataFragment: DataFragment): Fragment

    @Binds
    abstract fun bindFragmentFactory(factory: InjectingFragmentFactory): FragmentFactory
}