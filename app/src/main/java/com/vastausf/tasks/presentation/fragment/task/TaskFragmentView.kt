package com.vastausf.tasks.presentation.fragment.task

import com.vastausf.tasks.presentation.fragment.base.BaseFragmentView

interface TaskFragmentView: BaseFragmentView {

    fun updateLoadState()

    fun updateEditState()

    fun bindTaskData()

}
