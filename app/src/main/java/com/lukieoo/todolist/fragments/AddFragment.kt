package com.lukieoo.todolist.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.lukieoo.todolist.R
import com.lukieoo.todolist.model.Todo
import kotlinx.android.synthetic.main.fragment_add.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap


class AddFragment @Inject constructor() : Fragment(R.layout.fragment_add) {

    private lateinit var navController: NavController

    @Inject
    lateinit var db: FirebaseFirestore

    @Inject
    lateinit var docRef: CollectionReference

    private lateinit var todo: Todo

    private var photoUrl: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)

    }

    private fun initView(view: View) {


        val bundle = this.arguments

        navController = Navigation.findNavController(view)

        my_toolbar.setNavigationOnClickListener(View.OnClickListener {
            navController.popBackStack()
        })

        initBundle(bundle)

        initPhoto()

        addData.setOnClickListener {
            firebaseLogic()
        }
    }

    private fun initBundle(bundle: Bundle?) {
        if (bundle != null) {
            if (bundle.containsKey("todo"))
            try {
                todo = bundle.getSerializable("todo") as Todo

                itTitle.text = todo.title.toEditable()
                itDescription.text = todo.description.toEditable()

                if (todo.photo.trim() != "") {
                    when (todo.photo.trim()) {
                        "https://anioncode.pl/miquido/run.png" -> {
                            photoType1.foreground =
                                resources.getDrawable(R.drawable.ic_check_circle, null)
                        }
                        "https://anioncode.pl/miquido/work.png" -> {
                            photoType2.foreground =
                                resources.getDrawable(R.drawable.ic_check_circle, null)
                        }
                        "https://anioncode.pl/miquido/read.png" -> {
                            photoType3.foreground =
                                resources.getDrawable(R.drawable.ic_check_circle, null)
                        }
                        else -> {
                            itUrl.text = todo.photo.trim().toEditable()
                        }

                    }

                }

            } catch (e: TypeCastException) {
                e.printStackTrace()
            }

        }
    }

    private fun backToMainView() {

        hideKeyboard(requireActivity())
        if (navController.currentDestination?.id == R.id.addFragment) {
            navController.popBackStack()
        }
    }

    private fun firebaseLogic() {
        progressBarShowHide(View.VISIBLE)
        val todoPost: MutableMap<String, Any> = HashMap()
        todoPost["title"] = itTitle.text.toString()
        todoPost["description"] = itDescription.text.toString()
        todoPost["photo"] =
            if (itUrl.text.toString().trim() != "") itUrl.text.toString().trim() else photoUrl
        todoPost["date"] = Calendar.getInstance().timeInMillis.toString()

        if (::todo.isInitialized) {

            docRef.document(todo.id).update(todoPost).addOnSuccessListener {
                Toast.makeText(
                    requireContext(),
                    "Task updated with success",
                    Toast.LENGTH_LONG
                ).show()
                Log.d(
                    "Firestore",
                    "DocumentSnapshot updated with ID: " + todo.description.length
                )
                backToMainView()
            }.addOnFailureListener { e ->
                Log.w(
                    "Firestore", "Error adding document", e
                )
                Toast.makeText(requireContext(), "Error updating task", Toast.LENGTH_LONG)
                    .show()
                progressBarShowHide(View.GONE)

            }

        } else {

            db.collection("todoList")
                .add(todoPost)
                .addOnSuccessListener { documentReference ->
                    Log.d(
                        "Firestore",
                        "DocumentSnapshot added with ID: " + documentReference.id
                    )
                    Toast.makeText(
                        requireContext(),
                        "Task added with success",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    backToMainView()
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error adding document", e)
                    Toast.makeText(requireContext(), "Error adding task", Toast.LENGTH_LONG)
                        .show()
                    progressBarShowHide(View.GONE)
                }

        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    private fun initPhoto() {
        photoType1.setOnClickListener {
            photoType1.foreground = resources.getDrawable(R.drawable.ic_check_circle, null)
            photoType2.foreground = null
            photoType3.foreground = null
            itUrl.text = "".toEditable()
            photoUrl = "https://anioncode.pl/miquido/run.png"
        }
        photoType2.setOnClickListener {
            photoType1.foreground = null
            photoType2.foreground = resources.getDrawable(R.drawable.ic_check_circle, null)
            photoType3.foreground = null
            itUrl.text = "".toEditable()
            photoUrl = "https://anioncode.pl/miquido/work.png"
        }
        photoType3.setOnClickListener {
            photoType1.foreground = null
            photoType2.foreground = null
            photoType3.foreground = resources.getDrawable(R.drawable.ic_check_circle, null)
            itUrl.text = "".toEditable()
            photoUrl = "https://anioncode.pl/miquido/read.png"
        }
        itUrl.setOnFocusChangeListener { v, hasFocus ->
            photoType1.foreground = null
            photoType2.foreground = null
            photoType3.foreground = null
        }
    }

    fun progressBarShowHide(visibility: Int) {
        progress_add.visibility = visibility
    }

    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}