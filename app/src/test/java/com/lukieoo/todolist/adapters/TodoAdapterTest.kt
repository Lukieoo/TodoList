package com.lukieoo.todolist.adapters

import android.content.Context
import android.view.View
import com.google.firebase.firestore.CollectionReference
import com.lukieoo.todolist.model.Todo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit


class TodoAdapterTest{

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var docRef: CollectionReference

    lateinit var items: ArrayList<Todo>

    @Before
    fun setUp() {

        items = ArrayList()
        items.add(Todo("my title1","my description1","null","10.10.2220","1"))
        items.add(Todo("my title2","my description2","null","10.10.2220","2"))

    }

    @Test
    fun test1() {

        var mailingAdapter = TodoAdapter(context,docRef = docRef,itemClick = object :
            TodoAdapter.OnClickAdapterListener{
            override fun onClick(todo: Todo, view: View) {
            }

            override fun onLongClick(todo: Todo, view: View, docRef: CollectionReference) {
            }

        })

        mailingAdapter.setItems(items)

    }
}