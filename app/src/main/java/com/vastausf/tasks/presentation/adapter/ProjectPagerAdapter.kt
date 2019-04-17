package com.vastausf.tasks.presentation.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.vastausf.tasks.presentation.adapter.pages.FragmentProjectMain
import com.vastausf.tasks.presentation.adapter.pages.FragmentUsers

class ProjectPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    val main = FragmentProjectMain()
    val users = FragmentUsers()

    private val pages = listOf(
        users,
        main
    )

    override fun getItem(index: Int): Fragment {
        return pages[index]
    }

    override fun getCount(): Int {
        return pages.size
    }

}
