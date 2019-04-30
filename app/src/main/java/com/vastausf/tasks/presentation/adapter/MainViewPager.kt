package com.vastausf.tasks.presentation.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.vastausf.tasks.presentation.fragment.projectList.ProjectListFragment
import com.vastausf.tasks.presentation.fragment.taskList.TaskListFragment

class MainViewPager(
    fragmentManager: FragmentManager
): FragmentPagerAdapter(fragmentManager) {
    val projectList = ProjectListFragment()
    val taskList = TaskListFragment()

    private val pages = listOf(
        projectList,
        taskList
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

}