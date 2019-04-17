package com.vastausf.tasks.presentation.fragment.project

import com.vastausf.tasks.model.api.tasksApiData.ProjectDataFull
import com.vastausf.tasks.presentation.fragment.base.BaseFragmentView

interface ProjectFragmentView: BaseFragmentView {

    fun bindProjectData(projectDataFull: ProjectDataFull)

    fun projectLoadStatus(status: Boolean)

}
