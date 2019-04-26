package com.vastausf.tasks.presentation.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.vastausf.tasks.presentation.adapter.pages.FragmentProjectMain
import com.vastausf.tasks.presentation.adapter.pages.FragmentTasks
import com.vastausf.tasks.presentation.adapter.pages.FragmentUsers

class ProjectPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    val users = FragmentUsers()
    val main = FragmentProjectMain()
    val tasks = FragmentTasks()

    private val pages = listOf(
        users,
        main,
        tasks
    )

    override fun getItem(index: Int): Fragment {
        return pages[index]
    }

    override fun getCount(): Int {
        return pages.size
    }

}
