package com.lukieoo.todolist

import android.util.Log
import com.lukieoo.todolist.events.NavEvent
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.processors.PublishProcessor
import org.junit.Before
import org.junit.Test


class MyUnitTest {

    lateinit var navEvents: PublishProcessor<NavEvent>

    @Before
    fun setBefore(){

        navEvents = PublishProcessor.create()
    }

    @Test
    fun testEvent(){

        navEvents.subscribe {
            when (it.destination) {
                NavEvent.Destination.ADD ->   print("ADD")
                NavEvent.Destination.DATA ->   print("DATA")
            }
        }

        RxJavaPlugins.setErrorHandler { throwable: Throwable ->
         print(throwable)
        }

        navEvents.onNext(
            NavEvent(
                NavEvent.Destination.ADD
            )
        )

    }
}
