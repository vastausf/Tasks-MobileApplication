package com.vastausf.tasks.presentation.fragment.project

import com.vastausf.tasks.presentation.fragment.base.BaseFragmentView

interface ProjectFragmentView : BaseFragmentView {

    fun bindProjectData()

    fun updateLoadStatus()

    fun updateEditMode()

}
