package com.vastausf.tasks.presentation.fragment.project

import com.arellomobile.mvp.InjectViewState
import com.vastausf.tasks.R
import com.vastausf.tasks.model.api.TasksApiClient
import com.vastausf.tasks.model.api.tasksApiData.ProjectDataEdit
import com.vastausf.tasks.model.api.tasksApiData.ProjectDataFull
import com.vastausf.tasks.model.api.tasksApiData.ProjectDataFullC
import com.vastausf.tasks.model.api.tasksApiData.ProjectEditC
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
    var projectId: Int = 0

    var loadStatus = false
        set(value) {
            field = value
            viewState.updateLoadStatus()
        }

    lateinit var projectData: ProjectDataFull

    fun loadProjectData() {
        loadStatus = true

        tasksApiClient
            .getProjectFullData(projectId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                loadStatus = false
            }
            .subscribe(this::onLoadProjectDataSuccess, this::onLoadProjectDataError)
            .unsubscribeOnDestroy()
    }

    private fun onLoadProjectDataSuccess(data: ProjectDataFullC) {
        projectData = data.data

        viewState.bindProjectData()
    }

    private fun onLoadProjectDataError(error: Throwable) {
        viewState.showToast(R.string.error)
        error.printStackTrace()
    }

}