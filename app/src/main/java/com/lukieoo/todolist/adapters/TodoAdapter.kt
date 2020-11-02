package com.lukieoo.todolist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukieoo.todolist.R
import com.lukieoo.todolist.model.Todo
import kotlinx.android.synthetic.main.item_todo.view.*
import javax.inject.Inject

class TodoAdapter   (var itemClick:OnClickAdapterListener) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    lateinit var items: ArrayList<Todo>

    interface  OnClickAdapterListener{
        fun onClick( todo: Todo)
    }

    override fun getItemCount(): Int {
        if (::items.isInitialized) {

            return items.size
        } else {
            return 0
        }

    }

    fun setPosts(items: List<Todo>) {
        this.items = items as ArrayList<Todo>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var model = items.get(position)
        holder.itTitle.text = model.title
        holder.itDate.text = model.date
        holder.itDescription.text = model.description

       // holder.itPhoto.text = model
        holder.itemView.setOnClickListener {
            itemClick.onClick(model)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each picture to

        val itTitle = view.itTitle
        val itDate = view.itDate
        val itDescription = view.itDescription
        val itPhoto = view.itPhoto

    }
}
