package com.lukieoo.todolist.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.lukieoo.todolist.R
import kotlinx.android.synthetic.main.fragment_add.*
import javax.inject.Inject

class AddFragment @Inject constructor(): Fragment(R.layout.fragment_add){
    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentTest.setOnClickListener {
            navController = Navigation.findNavController(it)
            if (navController.currentDestination?.id == R.id.addFragment) {
                navController.popBackStack()
            }
        }

    }
}