package com.lukieoo.todolist.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
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

        var todoAdapter: TodoAdapter = TodoAdapter(requireContext(),itemClick = object :
            TodoAdapter.OnClickAdapterListener {

            override fun onClick(todo: Todo) {
                navController = Navigation.findNavController(view)
                if (navController.currentDestination?.id == R.id.dataFragment) {
                    val bundle = Bundle()

                    bundle.putSerializable("todo", todo)

                    navController.navigate(R.id.action_dataFragment_to_addFragment, bundle)

                }
            }

            override fun onLongClick(todo: Todo) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Delete item")
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton(
                        android.R.string.yes,
                        DialogInterface.OnClickListener { dialog, which ->
                            docRef.document(todo.id).delete()
                        })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(R.drawable.ic_delete)
                    .show()
            }

        })

        var items: MutableList<Todo> = arrayListOf()

        swipeRefreshLayout.isRefreshing=true

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

            swipeRefreshLayout.isRefreshing=false
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
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false;
        }

    }

    override fun onPause() {
        registration.remove()
        super.onPause()


    }
}