package com.vastausf.tasks.presentation.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.vastausf.tasks.presentation.fragment.project.ProjectFragment
import com.vastausf.tasks.presentation.fragment.taskList.TaskListFragment

class ProjectViewPagerAdapter(
    fragmentManager: FragmentManager
): FragmentPagerAdapter(fragmentManager) {
    val project = ProjectFragment()
    val projectTasks = TaskListFragment()

    private val pages = listOf(
        project,
        projectTasks
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

}