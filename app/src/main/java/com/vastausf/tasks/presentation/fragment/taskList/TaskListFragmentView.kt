package com.vastausf.tasks.presentation.fragment.taskList

import com.vastausf.tasks.presentation.fragment.base.BaseFragmentView

interface TaskListFragmentView: BaseFragmentView {

    fun updateLoadState()

    fun updateSearchState()

    fun bindTaskList()

}
