package com.lukieoo.todolist.model

import java.io.Serializable

data class Todo (
    var title:String="",
    var description:String="",
    var photo:String="",
    var date:String="",
    var id:String=""
) : Serializable