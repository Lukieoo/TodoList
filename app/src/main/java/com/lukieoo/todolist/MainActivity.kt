package com.lukieoo.todolist

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.lukieoo.todolist.events.FeedbackEvent
import com.lukieoo.todolist.events.NavEvent
import dagger.android.AndroidInjection
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navEvents: PublishProcessor<NavEvent>

    @Inject
    lateinit var feedbackEvent: PublishProcessor<FeedbackEvent>

    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {

        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.main_fragment_container)
        observersEvent()

    }

    @SuppressLint("CheckResult")
    private fun observersEvent() {
        navEvents.subscribe {
            when (it.destination) {
                NavEvent.Destination.ADD -> navController.popBackStack(R.id.addFragment, true)
                NavEvent.Destination.DATA -> navController.navigate(R.id.action_dataFragment_to_addFragment)
            }
        }
        feedbackEvent.subscribe {

            when (it.state) {
                FeedbackEvent.State.SUCCESS ->
                    Toast.makeText(
                        this,
                        it.title,
                        Toast.LENGTH_LONG
                    ).show()

                FeedbackEvent.State.ERROR -> {
                    Toast.makeText(
                        this,
                        it.title,
                        Toast.LENGTH_LONG
                    ).show()

                    Log.w("CloudFirestore", it.info)
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun onPause() {
        feedbackEvent.unsubscribeOn(Schedulers.io())
        navEvents.unsubscribeOn(Schedulers.io())
        super.onPause()
    }
}