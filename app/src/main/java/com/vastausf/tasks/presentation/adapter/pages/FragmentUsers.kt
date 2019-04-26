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
import kotlinx.android.synthetic.main.fragment_project_users.*

class FragmentUsers : Fragment() {

    private val userList = mutableListOf<UserData>()

    lateinit var usersAdapter: UsersAdapter

    fun bindUsers(users: List<UserData>) {
        userList.clear()
        userList.addAll(users)
        usersAdapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usersAdapter = UsersAdapter(userList)
        usersAdapter.listener = parentFragment as UsersAdapter.UserListener

        rvUsers.adapter = usersAdapter
        rvUsers.layoutManager = LinearLayoutManager(this.context)
    }

}
