package com.lukieoo.todolist.adapters

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lukieoo.todolist.R
import com.lukieoo.todolist.model.Todo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_todo.view.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class TodoAdapter(var context: Context, var itemClick: OnClickAdapterListener) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    lateinit var items: ArrayList<Todo>

    interface OnClickAdapterListener {
        fun onClick(todo: Todo)
        fun onLongClick(todo: Todo)
    }

    fun setPosts(items: List<Todo>) {
        this.items = items as ArrayList<Todo>
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

        var model = items.get(position)

        holder.itTitle.text = model.title
        holder.itDate.text = getDate(model.date.toLong(), "dd/MM/yyyy HH:mm")

        if (model.photo.trim()!=""){
            Picasso.get().load(model.photo).placeholder(context.getDrawable(R.drawable.holder)!!).into(holder.itPhoto)
        }
        if (model.description.trim() != "") {
            holder.itDescription.text = model.description
            holder.itDescription.visibility = View.VISIBLE
        } else {
            holder.itDescription.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            itemClick.onClick(model)
        }

        holder.itemView.setOnLongClickListener {
            itemClick.onLongClick(model)
            false
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itTitle: TextView = view.itTitle
        val itDate: TextView = view.itDate
        val itDescription: TextView = view.itDescription
        val itPhoto :ImageView= view.itPhoto

    }

    private fun getDate(milliSeconds: Long, dateFormat: String): String {
        var formatter: SimpleDateFormat = SimpleDateFormat(dateFormat)
        var calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.getTime())
    }
}
