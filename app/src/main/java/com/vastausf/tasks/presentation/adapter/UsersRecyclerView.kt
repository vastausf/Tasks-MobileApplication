@file:SuppressLint("SetTextI18n")

package com.vastausf.tasks.presentation.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vastausf.tasks.R
import com.vastausf.tasks.model.api.tasksApiData.TaskDataFull
import com.vastausf.tasks.model.api.tasksApiData.UserData
import kotlinx.android.synthetic.main.item_task.view.*
import kotlinx.android.synthetic.main.item_user.view.*

class UsersRecyclerView(
    val listener: UserListener,
    private val itemList: List<UserData>
) : RecyclerView.Adapter<UsersRecyclerView.ViewHolder>() {

    interface UserListener {
        fun onUserClick(userData: UserData, view: View)

        fun onUserLongClick(userData: UserData, view: View) {

        }
    }

    override fun onCreateViewHolder(container: ViewGroup, layoutType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(container.context).inflate(R.layout.item_user, container, false)
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(itemList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: UserData) {
            itemView.tvName.text = "${item.firstName} ${item.lastName}"
            itemView.tvEmail.text = item.email

            itemView.setOnClickListener {
                listener.onUserClick(item, itemView)
            }

            itemView.setOnLongClickListener{
                listener.onUserLongClick(item, itemView)

                return@setOnLongClickListener true
            }
        }

    }

}