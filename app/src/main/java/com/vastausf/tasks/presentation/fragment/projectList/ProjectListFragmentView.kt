package com.vastausf.tasks.presentation.fragment.projectList

import com.vastausf.tasks.presentation.fragment.base.BaseFragmentView

interface ProjectListFragmentView: BaseFragmentView {

    fun bindProjectList()

    fun updateLoadState()

    fun updateSearchState()

}
