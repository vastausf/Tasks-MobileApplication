package com.vastausf.tasks.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vastausf.tasks.R
import com.vastausf.tasks.model.api.tasksApiData.ProjectDataFull
import kotlinx.android.synthetic.main.item_project.view.*

class ProjectsAdapter(
    val listener: ProjectListener,
    private val itemList: List<ProjectDataFull>
) : RecyclerView.Adapter<ProjectsAdapter.ViewHolder>() {

    interface ProjectListener {
        fun onProjectClick(projectDataFull: ProjectDataFull)
        fun onLoadLast()
    }

    override fun onCreateViewHolder(container: ViewGroup, layoutType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(container.context).inflate(R.layout.item_project, container, false)
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (itemList.size - 1 == position) {
            listener.onLoadLast()
        }
        viewHolder.bind(itemList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: ProjectDataFull) {
            itemView.tvTitle.text = item.title
            itemView.tvDescription.text = item.description

            itemView.setOnClickListener {
                listener.onProjectClick(item)
            }
        }

    }

}