package com.vastausf.tasks.presentation.adapter

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vastausf.tasks.R
import com.vastausf.tasks.model.api.tasksApiData.UserData
import kotlinx.android.synthetic.main.fragment_project_users.view.*

class UsersBottomSheetDialog : BottomSheetDialogFragment(), UsersAdapter.UserListener {
    interface UsersListener {

        fun onUserClick(data: UserData)

    }

    var listener: UsersListener? = null

    val userList = mutableListOf<UserData>()

    private val usersAdapter = UsersAdapter(userList)

    fun bindUsers(users: List<UserData>) {
        userList.clear()
        userList.addAll(users)
        usersAdapter.notifyDataSetChanged()
    }

    override fun onUserClick(userData: UserData) {
        listener?.onUserClick(userData)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_project_users, container, false)

        view.apply {
            rvUsers.adapter = usersAdapter
            rvUsers.layoutManager = LinearLayoutManager(context)
        }

        return view
    }

}