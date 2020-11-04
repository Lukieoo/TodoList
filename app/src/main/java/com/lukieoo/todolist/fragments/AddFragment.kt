package com.lukieoo.todolist.fragments

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.lukieoo.todolist.R
import com.lukieoo.todolist.model.Todo
import com.lukieoo.todolist.presenters.AddFragmentPresenter
import com.lukieoo.todolist.utils.SystemUtils
import kotlinx.android.synthetic.main.fragment_add.*
import java.util.*
import javax.inject.Inject


class AddFragment @Inject constructor() : Fragment(R.layout.fragment_add),
    AddFragmentPresenter.View {

    @Inject
    lateinit var db: FirebaseFirestore

    @Inject
    lateinit var docRef: CollectionReference

    private lateinit var todo: Todo

    private var photoUrl: String = ""

    private val urlForExample: List<String> = listOf(
        "https://anioncode.pl/miquido/run.png",
        "https://anioncode.pl/miquido/work.png",
        "https://anioncode.pl/miquido/read.png"
    )
    lateinit var drawableChoose: Drawable

    private lateinit var presenter: AddFragmentPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = AddFragmentPresenter(this)
        drawableChoose = resources.getDrawable(R.drawable.ic_check_circle, null)

        initBundle()
        initPhoto()
        initView()
    }

    private fun initView() {

        addData.setOnClickListener {
            presenter.updateTodoPost(
                title = itTitle.text.toString(),
                description = itDescription.text.toString(),
                photo = if (itUrl.text.toString().trim() != "") itUrl.text.toString()
                    .trim() else photoUrl,
                date = Calendar.getInstance().timeInMillis.toString(),
                callback = it

            )
        }
        my_toolbar.setNavigationOnClickListener(View.OnClickListener {
            presenter.navigateBack(it)
        })
    }

    private fun initBundle() {

        val bundle = this.arguments
        if (bundle != null) {
            if (bundle.containsKey("todo"))
                try {
                    todo = bundle.getSerializable("todo") as Todo
                    presenter.updateTodoBundle(todo)

                } catch (e: TypeCastException) {
                    e.printStackTrace()
                }

        }
    }

    private fun initPhoto() {

        photoType1.setOnClickListener {
            resetForeground()
            photoType1.foreground = drawableChoose
            itUrl.text = "".toEditable()
            photoUrl = urlForExample[0]
        }
        photoType2.setOnClickListener {
            resetForeground()
            photoType2.foreground = drawableChoose
            itUrl.text = "".toEditable()
            photoUrl = urlForExample[1]
        }
        photoType3.setOnClickListener {
            resetForeground()
            photoType3.foreground = drawableChoose
            itUrl.text = "".toEditable()
            photoUrl = urlForExample[2]
        }
        itUrl.setOnFocusChangeListener { v, hasFocus ->
            resetForeground()
        }
    }

    private fun resetForeground() {
        photoType1.foreground = null
        photoType2.foreground = null
        photoType3.foreground = null
    }


    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    override fun setFromBundleView(todoBundle: Todo) {

        itTitle.text = todo.title.toEditable()
        itDescription.text = todo.description.toEditable()

        if (todo.photo.trim() != "") {
            when (todo.photo.trim()) {
                urlForExample[0] -> {
                    photoType1.foreground =
                        drawableChoose
                }
                urlForExample[1] -> {
                    photoType2.foreground =
                        drawableChoose
                }
                urlForExample[2] -> {
                    photoType3.foreground =
                        drawableChoose
                }
                else -> {
                    itUrl.text = todo.photo.trim().toEditable()
                }

            }

        }
    }

    override fun setPostDataForFireStore(
        todoPost: MutableMap<String, Any>,
        id: String,
        callback: View
    ) {
        showProgressBar()
        if (::todo.isInitialized) {

            docRef.document(todo.id).update(todoPost).addOnSuccessListener {
                showEvent("Task updated with success", todo.id)
                presenter.navigateBack(callback)
            }.addOnFailureListener { e ->
                showEvent("Error adding task", e.printStackTrace().toString())
                hideProgressBar()
            }
        } else {
            db.collection("todoList")
                .add(todoPost)
                .addOnSuccessListener { documentReference ->
                    showEvent("Task added with success", documentReference.id)
                    presenter.navigateBack(callback)
                }
                .addOnFailureListener { e ->

                    showEvent("Error adding task", e.printStackTrace().toString())
                    hideProgressBar()
                }

        }
    }

    override fun showProgressBar() {
        progress_add.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progress_add.visibility = View.GONE
    }

    override fun showEvent(title: String, info: String) {

        Log.w("CloudFirestore", "$info")

        try {
            Toast.makeText(
                requireContext(),
                title,
                Toast.LENGTH_LONG
            ).show()
        } catch (e: NullPointerException) {

            Log.w("CloudFirestore", e.printStackTrace().toString())
        }

    }

    override fun navigatePopBack(callback: View) {
        try {
            var navController: NavController = Navigation.findNavController(callback)
            SystemUtils.hideKeyboard(requireActivity())
            if (navController.currentDestination?.id == R.id.addFragment) {
                navController.popBackStack()
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

    }


}