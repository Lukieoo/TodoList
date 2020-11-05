package com.lukieoo.todolist.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.lukieoo.todolist.R
import com.lukieoo.todolist.adapters.TodoAdapter
import com.lukieoo.todolist.events.FeedbackEvent
import com.lukieoo.todolist.events.NavEvent
import com.lukieoo.todolist.model.Todo
import com.lukieoo.todolist.presenters.MainFragmentPresenter
import io.reactivex.processors.PublishProcessor
import kotlinx.android.synthetic.main.fragment_data.*
import java.lang.Exception
import javax.inject.Inject


class DataFragment @Inject constructor() : Fragment(R.layout.fragment_data),
    MainFragmentPresenter.View {

    @Inject
    lateinit var docRef: CollectionReference
    @Inject
    lateinit var todoAdapter: TodoAdapter
    @Inject
    lateinit var navEvents: PublishProcessor<NavEvent>
    @Inject
    lateinit var feedbackEvent: PublishProcessor<FeedbackEvent>

    private lateinit var registration: ListenerRegistration
    private lateinit var registrationPagination: ListenerRegistration
    private lateinit var presenter: MainFragmentPresenter

    private var isEnd = false
    private var snapshotTmp: DocumentSnapshot? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = MainFragmentPresenter(this)

        fetchDataFromFirebase()
        initView()
    }

    private fun initView() {

        val layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        todo_list.layoutManager = layoutManager
        todo_list.adapter = todoAdapter

        isEnd = false
        todo_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    if (!isEnd) {
                        isEnd = true
                        if (todoAdapter.itemCount > 0) {
                            showProgressBar()
                            loadMoreItems()

                        }
                    }

                }
            }
        })

        add_btn.setOnClickListener {
            presenter.navigateToAdd(it)
        }

        swipeRefreshLayout.setOnRefreshListener {
            hideProgressBar()
        }
    }

    private fun fetchDataFromFirebase() {

        val items: MutableList<Todo> = arrayListOf()
        showProgressBar()

        registration = docRef.orderBy("date", Query.Direction.DESCENDING).limit(30)
            .addSnapshotListener { snapshot, e ->
                if (e == null) {
                    val list: List<DocumentSnapshot> =
                        snapshot!!.documents
                    items.clear()
                    for (d: DocumentSnapshot in list) {
                        val todo: Todo = d.toObject(Todo::class.java)!!
                        todo.id = d.id
                        items.add(todo)
                    }
                    snapshotTmp = if (list.isNotEmpty()) {
                        list[list.size - 1]
                    } else {
                        null
                    }
                    presenter.updateListTodo(items)
                } else {
                    presenter.setOnError(e)
                }
                hideProgressBar()
            }

        presenter.updateListTodo(items)

    }

    private fun loadMoreItems() {
        registrationPagination =
            docRef.orderBy("date", Query.Direction.DESCENDING).limit(30).startAt(
                snapshotTmp!!.data!!["date"]
            ).addSnapshotListener { snapshot, e ->
                if (snapshot != null) {

                    val list: List<DocumentSnapshot> =
                        snapshot.documents
                    val items2: MutableList<Todo> = arrayListOf()

                    for (d: DocumentSnapshot in list) {
                        val todo: Todo = d.toObject(Todo::class.java)!!
                        todo.id = d.id
                        items2.add(todo)
                    }
                    if (items2.isNotEmpty()) {
                        items2.removeAt(0)
                    }
                    if (list.isNotEmpty()) {
                        if (snapshotTmp == null) {
                            todo_list.scrollToPosition(todoAdapter.itemCount - 1)
                            todoAdapter.addItems(items2)
                            isEnd = true
                        } else {
                            if (snapshotTmp!!.data!!["date"]
                                ==
                                list[list.size - 1].data!!["date"]
                            ) {
                                isEnd = true
                                snapshotTmp = null
                            } else {
                                todo_list.scrollToPosition(todoAdapter.itemCount - 1)
                                todoAdapter.addItems(items2)
                                snapshotTmp = list[list.size - 1]
                                isEnd = false
                            }
                        }

                    } else {
                        todo_list.scrollToPosition(todoAdapter.itemCount - 1)
                        todoAdapter.addItems(items2)
                        isEnd = true
                        snapshotTmp = null
                    }

                }
                hideProgressBar()

            }
    }

    override fun onPause() {
        registration.remove()
        if (::registrationPagination.isInitialized) {
            registrationPagination.remove()
        }
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
        feedbackEvent.onNext(
            FeedbackEvent(
                FeedbackEvent.State.ERROR, "Something gone wrong", exception.toString()
            )
        )
    }

    override fun navigateToAddFragment(callback: View) {
        navEvents.onNext(
            NavEvent(
                NavEvent.Destination.DATA
            )
        )
    }

}