package com.lukieoo.todolist.adapters

import android.content.Context
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.CollectionReference
import com.lukieoo.todolist.R
import com.lukieoo.todolist.model.Todo
import com.lukieoo.todolist.utils.DataConverter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_todo.view.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import java.lang.Exception

class TodoAdapter @Inject constructor(
    var context: Context,
    var itemClick: OnClickAdapterListener,
    var docRef: CollectionReference
) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    lateinit var items: ArrayList<Todo>

    interface OnClickAdapterListener {
        fun onClick(todo: Todo, view: View)
        fun onLongClick(todo: Todo, view: View, docRef: CollectionReference)
    }

    fun setItems(items: List<Todo>) {
        this.items = items as ArrayList<Todo>

        try {
            notifyDataSetChanged()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

    }

    fun addItems(items: List<Todo>) {
        if (items.isNotEmpty()) {
            for (element in items) {
                for (elementTmp in 0 until this.items.size) {
                    try {
                        if (element.id == this.items[elementTmp].id) {
                            this.items.removeAt(elementTmp)
                        }
                    } catch (e: IndexOutOfBoundsException) {
                        e.printStackTrace()
                    }

                }
            }

            for (element in items) {
                this.items.add(element)
            }
        }

        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return if (::items.isInitialized) {

            items.size
        } else {
            0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var model = items[position]

        holder.itTitle.text = model.title
        holder.itDate.text = DataConverter.getDate(model.date.toLong(), "dd/MM/yyyy HH:mm")

        if (model.photo.trim() != "") {

            Picasso.get().load(model.photo)
                .resize(150, 150)
                .centerCrop()
                .placeholder(ContextCompat.getDrawable(context, R.drawable.holder)!!)
                .into(holder.itPhoto)

        } else {
            holder.itPhoto.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.holder)!!)

        }

        if (model.description.trim() != "") {

            holder.itDescription.text = model.description
            holder.itDescription.visibility = View.VISIBLE

        } else {
            holder.itDescription.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            itemClick.onClick(model, it)
        }

        holder.itemView.setOnLongClickListener {
            itemClick.onLongClick(model, it, docRef)
            false
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itTitle: TextView = view.itTitle
        val itDate: TextView = view.itDate
        val itDescription: TextView = view.itDescription
        val itPhoto: ImageView = view.itPhoto

    }


}
