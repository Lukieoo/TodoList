package com.lukieoo.todolist.di

import android.app.AlertDialog
import android.app.Application
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.lukieoo.todolist.R
import com.lukieoo.todolist.adapters.TodoAdapter
import com.lukieoo.todolist.model.Todo
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
object AdapterModule {


    @JvmStatic
    @Provides
    fun provideTodoAdapter(application: Application, docRef: CollectionReference): TodoAdapter {
        return TodoAdapter(application.applicationContext, itemClick = object :
            TodoAdapter.OnClickAdapterListener {

            override fun onClick(todo: Todo, view: View) {

                var navController: NavController = Navigation.findNavController(view)

                if (navController.currentDestination?.id == R.id.dataFragment) {
                    val bundle = Bundle()
                    bundle.putSerializable("todo", todo)
                    navController.navigate(R.id.action_dataFragment_to_addFragment, bundle)

                }
            }

            override fun onLongClick(todo: Todo, view: View, docRef: CollectionReference) {

                AlertDialog.Builder(view.context)
                    .setTitle("Delete item")
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton(
                        android.R.string.yes
                    ) { dialog, which ->
                        docRef.document(todo.id).delete()
                    }
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(R.drawable.ic_delete)
                    .show()
            }

        }, docRef = docRef)
    }

}