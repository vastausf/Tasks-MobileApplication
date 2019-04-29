package com.vastausf.tasks.presentation.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.vastausf.tasks.presentation.fragment.taskList.TaskListFragment

class TasksViewPagerAdapter(
    fragmentManager: FragmentManager
): FragmentPagerAdapter(fragmentManager) {
    val userTasks = TaskListFragment()
    val allTasks = TaskListFragment()

    private val pages = listOf(
        userTasks,
        allTasks
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

}