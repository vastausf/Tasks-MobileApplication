package com.vastausf.tasks.presentation.fragment.project

import com.arellomobile.mvp.InjectViewState
import com.vastausf.tasks.R
import com.vastausf.tasks.model.api.TasksApiClient
import com.vastausf.tasks.model.api.tasksApiData.ProjectDataSearch
import com.vastausf.tasks.model.api.tasksApiData.ProjectFindI
import com.vastausf.tasks.presentation.fragment.base.BaseFragmentPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class ProjectFragmentPresenter
@Inject
constructor(
    private val tasksApiClient: TasksApiClient
) : BaseFragmentPresenter<ProjectFragmentView>() {

    fun loadProjectData(projectId: Int?) {
        if (projectId == null) {
            viewState.showToast(R.string.error)
            viewState.goBack()
        } else {
            viewState.projectLoadStatus(true)

            tasksApiClient
                .getProjectList(0, 1, ProjectDataSearch(projectId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    viewState.projectLoadStatus(false)
                }
                .subscribe(this::onLoadProjectDataSuccess, this::onLoadProjectDataError)
                .unsubscribeOnDestroy()
        }
    }

    private fun onLoadProjectDataSuccess(data: ProjectFindI) {
        viewState.bindProjectData(data.data.first())
    }

    private fun onLoadProjectDataError(error: Throwable) {
        viewState.showToast(R.string.error)
        error.printStackTrace()
    }

}