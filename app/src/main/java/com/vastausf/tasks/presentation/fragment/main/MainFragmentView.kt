package com.vastausf.tasks.presentation.fragment.main

import com.vastausf.tasks.model.api.tasksApiData.ProjectDataShort
import com.vastausf.tasks.presentation.fragment.base.BaseFragmentView

interface MainFragmentView : BaseFragmentView {

    fun bindProjectList(data: List<ProjectDataShort>)

    fun projectsLoadStatus(status: Boolean)

}
