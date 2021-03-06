@file:SuppressLint("SetTextI18n")

package com.vastausf.tasks.presentation.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vastausf.tasks.R
import com.vastausf.tasks.model.api.tasksApiData.TaskDataFull
import com.vastausf.tasks.model.api.tasksApiData.TaskStatus
import kotlinx.android.synthetic.main.item_task.view.*

class TasksRecyclerView(
    val listener: TaskListener,
    private val itemList: List<TaskDataFull>
) : RecyclerView.Adapter<TasksRecyclerView.ViewHolder>() {

    interface TaskListener {
        fun onTaskClick(taskData: TaskDataFull)
    }

    override fun onCreateViewHolder(container: ViewGroup, layoutType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(container.context).inflate(R.layout.item_task, container, false)
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(itemList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: TaskDataFull) {
            itemView.tvTitle.text = item.title
            itemView.tvStatus.setText(TaskStatus.fromInt(item.status))
            itemView.tvDescription.text = item.description

            itemView.setOnClickListener {
                listener.onTaskClick(item)
            }
        }

    }

}