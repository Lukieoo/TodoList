package com.lukieoo.todolist.model

import kotlinx.android.synthetic.main.item_todo.view.*
import java.io.Serializable

data class Todo (
    var title:String="",
    var description:String="",
    var photo:String="",
    var date:String="",
    var id:String=""
) : Serializable