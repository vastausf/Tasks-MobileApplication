package com.vastausf.tasks.presentation.adapter.pages

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vastausf.tasks.R
import com.vastausf.tasks.model.api.tasksApiData.UserData
import com.vastausf.tasks.presentation.adapter.UsersAdapter
import kotlinx.android.synthetic.main.fragment_project_users.view.*

class FragmentUsers : Fragment() {

    private val userList = mutableListOf<UserData>()

    fun bindUsers(listener: UsersAdapter.UserListener, users: List<UserData>) {
        userList.clear()
        userList.addAll(users)

        view?.rvUsers?.apply {
            adapter = UsersAdapter(listener, userList)
            layoutManager = LinearLayoutManager(this.context)
            adapter?.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_project_users, container, false)

        return view
    }

}