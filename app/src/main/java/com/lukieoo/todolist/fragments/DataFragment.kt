package com.lukieoo.todolist.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.lukieoo.todolist.R
import com.lukieoo.todolist.adapters.TodoAdapter
import com.lukieoo.todolist.model.Todo
import com.lukieoo.todolist.presenters.MainFragmentPresenter
import kotlinx.android.synthetic.main.fragment_data.*
import java.lang.Exception
import java.util.*
import javax.inject.Inject


class DataFragment @Inject constructor() : Fragment(R.layout.fragment_data),
    MainFragmentPresenter.View {

    @Inject
    lateinit var docRef: CollectionReference

    @Inject
    lateinit var todoAdapter: TodoAdapter

    private lateinit var registration: ListenerRegistration
    private lateinit var presenter: MainFragmentPresenter

    var isEnd = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = MainFragmentPresenter(this)

        fetchData()
        initView()

    }

    private fun initView() {
        isEnd = false
        val layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                .apply {
                    //  stackFromEnd = true
                }

        todo_list.layoutManager = layoutManager
        todo_list.adapter = todoAdapter

        add_btn.setOnClickListener {
            presenter.navigateToAdd(it)
        }

        swipeRefreshLayout.setOnRefreshListener {
            hideProgressBar()
        }

    }

    private fun fetchData() {

        var items: MutableList<Todo> = arrayListOf()


        showProgressBar()
        var snapshotTmp: DocumentSnapshot? = null

        todo_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {

                    showProgressBar()
                    if (!isEnd) {
                        isEnd = true

                        if (todoAdapter.itemCount > 0) {


                            docRef.orderBy("date", Query.Direction.DESCENDING).limit(10).startAt(
                                snapshotTmp!!.data!!["date"]
                            ).get()
                                .addOnSuccessListener {
                                    if (it != null) {

                                        val list: List<DocumentSnapshot> =
                                            it!!.documents
                                        var items2: MutableList<Todo> = arrayListOf()
                                        //      items.clear()
                                        for (d: DocumentSnapshot in list) {
                                            var todo: Todo = d.toObject(Todo::class.java)!!
                                            todo.id = d.id
                                            items2.add(todo)

                                        }
                                        if (items2.isNotEmpty()) {
                                            items2.removeAt(0)
                                        }
                                        //        items2.reverse();

                                        println("${snapshotTmp!!.data}  KOTLIN")
                                        if (list.isNotEmpty()) {

                                            isEnd = false
                                            if (snapshotTmp!!.data!!["date"] == list[list.size - 1].data!!["date"]) {
                                                isEnd = true
                                            }else{
                                                todo_list.scrollToPosition(todoAdapter.itemCount - 1)
                                                todoAdapter.addItems(items2)
                                                snapshotTmp = list[list.size - 1]
                                            }



                                        } else {
                                            todo_list.scrollToPosition(todoAdapter.itemCount - 1)
                                            todoAdapter.addItems(items2)
                                            isEnd = true
                                            snapshotTmp = null
                                        }

                                    } else {
                                        //  presenter.setOnError(e)
                                    }
                                    hideProgressBar()
                                }
                            hideProgressBar()
                        }
                    } else {
                        hideProgressBar()
                    }

                }
            }
        })

        registration = docRef.orderBy("date", Query.Direction.DESCENDING).limit(10)
            .addSnapshotListener { snapshot, e ->

                if (e == null) {

                    val list: List<DocumentSnapshot> =
                        snapshot!!.documents
                    items.clear()
                    for (d: DocumentSnapshot in list) {
                        var todo: Todo = d.toObject(Todo::class.java)!!
                        todo.id = d.id
                        items.add(todo)
                    }

                    if (list.isNotEmpty()) {
                        snapshotTmp = list[list.size - 1]
                    } else {
                        snapshotTmp = null
                    }

                    presenter.updateListTodo(items)
                } else {
                    presenter.setOnError(e)
                }
                hideProgressBar()
            }

        presenter.updateListTodo(items)

    }

    override fun onPause() {
        registration.remove()
        super.onPause()
    }

    override fun setListTodo(listTodo: MutableList<Todo>) {
        todoAdapter.setItems(listTodo)
    }

    override fun showProgressBar() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideProgressBar() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onError(exception: Exception) {
        Toast.makeText(requireContext(), "Something gone wrong", Toast.LENGTH_LONG).show()
        exception.printStackTrace()
    }

    override fun navigateToAddFragment(callback: View) {
        var navController: NavController = Navigation.findNavController(callback)
        if (navController.currentDestination?.id == R.id.dataFragment) {
            navController.navigate(R.id.action_dataFragment_to_addFragment)
        }
    }

}