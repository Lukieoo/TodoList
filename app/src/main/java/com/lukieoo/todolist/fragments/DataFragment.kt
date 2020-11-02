package com.lukieoo.todolist.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration
import com.lukieoo.todolist.R
import com.lukieoo.todolist.adapters.TodoAdapter
import com.lukieoo.todolist.model.Todo
import kotlinx.android.synthetic.main.fragment_data.*
import javax.inject.Inject


class DataFragment @Inject constructor() : Fragment(R.layout.fragment_data) {

    lateinit var navController: NavController

    @Inject
    lateinit var docRef: CollectionReference

    private lateinit var registration: ListenerRegistration


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initView(view)

    }

    private fun initView(view: View) {

        var todoAdapter: TodoAdapter = TodoAdapter(itemClick = object :
            TodoAdapter.OnClickAdapterListener {

            override fun onClick(todo: Todo) {
                navController = Navigation.findNavController(view)
                if (navController.currentDestination?.id == R.id.dataFragment) {
                    val bundle = Bundle()

                    bundle.putSerializable("todo", todo)

                    navController.navigate(R.id.action_dataFragment_to_addFragment, bundle)

                }
            }

        })

        var items: MutableList<Todo> = arrayListOf()

        registration = docRef.orderBy("date").addSnapshotListener { snapshot, e ->

            val list: List<DocumentSnapshot> =
                snapshot!!.documents
            items.clear()

            for (d: DocumentSnapshot in list) {
                var todo: Todo = d.toObject(Todo::class.java)!!
                todo.id = d.id
                items.add(todo)

            }
            todoAdapter.notifyDataSetChanged()

        }

        todoAdapter.setPosts(items)

        val layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true).apply {
                stackFromEnd = true
            }

        todo_list.layoutManager = layoutManager
        todo_list.adapter = todoAdapter

        add_btn.setOnClickListener {
            navController = Navigation.findNavController(it)
            if (navController.currentDestination?.id == R.id.dataFragment) {
                navController.navigate(R.id.action_dataFragment_to_addFragment)
            }


        }
    }

    override fun onPause() {
        registration.remove()
        super.onPause()


    }
}