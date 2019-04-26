package com.vastausf.tasks.presentation.fragment.newProject

import com.arellomobile.mvp.InjectViewState
import com.vastausf.tasks.model.api.tasksApiData.ProjectDataCreate
import com.vastausf.tasks.presentation.fragment.base.BaseFragmentPresenter
import javax.inject.Inject

@InjectViewState
class NewProjectPresenter
@Inject
constructor(

) : BaseFragmentPresenter<NewProjectView>() {

    fun onCreateClick(data: ProjectDataCreate) {
        viewState.showToast(data)
    }


}
