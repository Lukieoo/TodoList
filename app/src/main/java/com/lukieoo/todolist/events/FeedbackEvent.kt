package com.lukieoo.todolist.events

import java.lang.Exception

/**
 * Feedback Events for RxJava to create Toast
 */
class FeedbackEvent(
    val state: State, var title: String, var info: String
) {
    enum class State {
        SUCCESS, ERROR
    }
}