package com.vastausf.tasks.presentation.fragment.main

import com.vastausf.tasks.model.api.tasksApiData.ProjectDataFull
import com.vastausf.tasks.presentation.fragment.base.BaseFragmentView

interface MainFragmentView: BaseFragmentView {

    fun bindProjectList(data: List<ProjectDataFull>, clean: Boolean = true)

    fun projectsLoadStatus(status: Boolean)

}
