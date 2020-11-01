package com.lukieoo.todolist.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.lukieoo.todolist.R
import kotlinx.android.synthetic.main.fragment_data.*
import javax.inject.Inject

class DataFragment @Inject constructor() : Fragment(R.layout.fragment_data){

    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add_btn.setOnClickListener {
            navController = Navigation.findNavController(it)
            if (navController.currentDestination?.id == R.id.dataFragment) {
                navController.navigate(R.id.action_dataFragment_to_addFragment)
            }
        }

    }
}