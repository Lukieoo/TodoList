package com.lukieoo.todolist.presenters

import android.view.View as Callback
import com.lukieoo.todolist.model.Todo
import java.lang.Exception

class MainFragmentPresenter(view: View) {

    private var listTodo: MutableList<Todo> = arrayListOf()
    private var view: View = view

    fun updateListTodo(listTodo: MutableList<Todo>) {
        this.listTodo=listTodo
        view.setListTodo(listTodo)
    }
    fun navigateToAdd(callback: Callback) {
        view.navigateToAddFragment(callback)
    }
    fun setOnError(exception: Exception){
        view.onError(exception)
    }
    interface View {
        fun setListTodo(listTodo: MutableList<Todo>)
        fun showProgressBar()
        fun hideProgressBar()
        fun onError(exception: Exception)
        fun navigateToAddFragment(callback: Callback)
    }
}