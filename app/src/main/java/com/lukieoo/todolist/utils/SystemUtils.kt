package com.lukieoo.todolist.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
/**
 * Object with hide keyboard method
 */
object SystemUtils {
    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}