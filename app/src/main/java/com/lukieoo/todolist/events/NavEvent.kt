package com.lukieoo.todolist.events

/**
 * Navigation Events for RxJava
 */
class NavEvent(
    val destination: Destination
) {
    enum class Destination {
        ADD, DATA
    }
}