package com.lukieoo.todolist.presenters

import android.content.Context
import android.view.View as Callback
import com.lukieoo.todolist.model.Todo
import kotlin.collections.HashMap

class AddFragmentPresenter(private var view: View) {

    private lateinit var todoBundle: Todo
    private var todoPost: MutableMap<String, Any> = HashMap()

    fun updateTodoBundle(todoBundle: Todo) {
        this.todoBundle = todoBundle
        view.setFromBundleView(todoBundle)
    }

    fun updateTodoPost(
        title: String = "",
        description: String = "",
        photo: String = "",
        date: String = "",
        id: String = "",
        callback: Callback
    ) {

        todoPost["title"] = title
        todoPost["description"] = description
        todoPost["photo"] = photo
        todoPost["date"] = date
        this.todoPost = todoPost
        view.setPostDataForFireStore(todoPost, id, callback)
    }

    fun navigateBack(callback: Callback) {
        view.navigatePopBack(callback)
    }

    interface View {
        fun setFromBundleView(todoBundle: Todo)
        fun showProgressBar()
        fun hideProgressBar()
        fun showEventSuccess(title: String, info: String)
        fun showEventError(title: String, info: String)
        fun navigatePopBack(callback: Callback)
        fun setPostDataForFireStore(
            todoPost: MutableMap<String, Any>,
            id: String,
            callback: Callback
        )
    }
}