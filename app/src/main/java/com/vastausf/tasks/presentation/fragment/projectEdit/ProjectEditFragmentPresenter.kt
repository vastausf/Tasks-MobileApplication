package com.vastausf.tasks.presentation.fragment.projectEdit

import com.arellomobile.mvp.InjectViewState
import com.vastausf.tasks.R
import com.vastausf.tasks.model.api.TasksApiClient
import com.vastausf.tasks.model.api.tasksApiData.*
import com.vastausf.tasks.presentation.fragment.base.BaseFragmentPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class ProjectEditFragmentPresenter
@Inject
constructor(
    private val tasksApiClient: TasksApiClient
): BaseFragmentPresenter<ProjectEditFragmentView>() {

    var projectId: Int = 0

    var projectDataEdit = ProjectDataEdit()

    val documentList = mutableListOf<String>()
    val userList = mutableListOf<UserData>()

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

        projectDataEdit = ProjectDataEdit(
            projectData.title,
            projectData.description,
            projectData.specification,
            projectData.credentials.map { it.id }.toMutableList(),
            projectData.documents.toMutableList()
        )

        documentList.apply {
            clear()
            addAll(projectData.documents)
        }

        userList.apply {
            clear()
            addAll(projectData.credentials)
        }

        viewState.bindProjectData()
    }

    private fun onLoadProjectDataError(error: Throwable) {
        viewState.showToast(R.string.error)
        error.printStackTrace()
    }

    fun editProjectData() {
        loadStatus = true

        projectDataEdit.documents = documentList
        projectDataEdit.credentials = userList.map { it.id }

        tasksApiClient
            .editProject(projectId, projectDataEdit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                loadStatus = false
            }
            .subscribe(this::onEditProjectDataSuccess, this::onEditProjectDataError)
            .unsubscribeOnDestroy()
    }

    private fun onEditProjectDataSuccess(data: ProjectEditC) {
        viewState.showToast(R.string.success)
        viewState.goBack()
    }

    private fun onEditProjectDataError(error: Throwable) {
        viewState.showToast(R.string.error)
        error.printStackTrace()
    }

}
