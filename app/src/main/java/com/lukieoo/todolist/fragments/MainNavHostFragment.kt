package com.lukieoo.todolist.fragments

import android.content.Context
import androidx.navigation.fragment.NavHostFragment
import com.lukieoo.todolist.navigation.InjectingFragmentFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainNavHostFragment: NavHostFragment() {

    @Inject
    protected lateinit var daggerFragmentInjectionFactory: InjectingFragmentFactory

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        childFragmentManager.fragmentFactory = daggerFragmentInjectionFactory
    }
}