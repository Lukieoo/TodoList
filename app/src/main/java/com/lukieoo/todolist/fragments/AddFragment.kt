package com.lukieoo.todolist.fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.firestore.FirebaseFirestore
import com.lukieoo.todolist.R
import com.lukieoo.todolist.model.Todo
import kotlinx.android.synthetic.main.fragment_add.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class AddFragment @Inject constructor() : Fragment(R.layout.fragment_add) {

    lateinit var navController: NavController

    @Inject
    lateinit var db: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

    }

    private fun initView() {
        val bundle = this.arguments

        if (bundle != null) {
            try {

                var todo: Todo = bundle.getSerializable("todo") as Todo

                itTitle.text = todo.title.toEditable()
                itDate.text = todo.date
                itDescription.text = todo.description.toEditable()

            } catch (e: TypeCastException) {
                itDate.text = Calendar.getInstance().timeInMillis.toString()
            }


        }

        addData.setOnClickListener {
            navController = Navigation.findNavController(it)
            if (navController.currentDestination?.id == R.id.addFragment) {
                navController.popBackStack()
            }

            val todoPost: MutableMap<String, Any> = HashMap()
            todoPost["title"] = itTitle.text.toString()
            todoPost["description"] = itDescription.text.toString()
            todoPost["photo"] = "1815"
            todoPost["date"] = Calendar.getInstance().timeInMillis.toString()

            db.collection("todoList")
                .add(todoPost)
                .addOnSuccessListener { documentReference ->
                    Log.d(
                        "Firestore",
                        "DocumentSnapshot added with ID: " + documentReference.id
                    )
                }
                .addOnFailureListener { e -> Log.w("Firestore", "Error adding document", e) }
        }
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}