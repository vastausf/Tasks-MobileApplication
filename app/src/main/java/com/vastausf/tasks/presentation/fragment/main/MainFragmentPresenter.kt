package com.vastausf.tasks.presentation.fragment.main

import com.arellomobile.mvp.InjectViewState
import com.vastausf.tasks.R
import com.vastausf.tasks.TasksApplication
import com.vastausf.tasks.model.api.TasksApiClient
import com.vastausf.tasks.model.api.tasksApiData.*
import com.vastausf.tasks.presentation.fragment.base.BaseFragmentPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject

@InjectViewState
class MainFragmentPresenter
@Inject
constructor(
    private val tasksApplication: TasksApplication,
    private val tasksApiClient: TasksApiClient
) : BaseFragmentPresenter<MainFragmentView>() {

    val projectList = mutableListOf<ProjectDataShort>()

    fun loadProjects(
        parameters: ProjectDataSearch = ProjectDataSearch()
    ) {
        viewState.projectsLoadStatus(true)

        tasksApiClient
            .getProjectList(parameters)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                viewState.projectsLoadStatus(false)
            }
            .subscribe(
                this::onLoadProjectsSuccess,
                this::onLoadProjectsError
            )
            .unsubscribeOnDestroy()
    }

    private fun onLoadProjectsSuccess(data: ProjectFindC) {
        projectList.clear()
        projectList.addAll(data.data)

        viewState.bindProjectList(data.data)
    }

    private fun onLoadProjectsError(error: Throwable) {
        when (error) {
            is HttpException -> viewState.showToast(error.code())

            else -> {
                viewState.showToast(error::class.java.name)
                viewState.showToast(R.string.error)
                error.printStackTrace()
            }
        }
    }

    fun createProject(title: String) {
        viewState.projectsLoadStatus(true)

        tasksApiClient
            .createNewProject(ProjectDataCreate(title, "", "", listOf(), listOf()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                viewState.projectsLoadStatus(false)
            }
            .subscribe(
                this::onCreateProjectSuccess,
                this::onCreateProjectError
            )
            .unsubscribeOnDestroy()
    }

    private fun onCreateProjectSuccess(data: ProjectNewC) {
        loadProjects()
    }

    private fun onCreateProjectError(error: Throwable) {
        when (error) {
            is HttpException -> viewState.showToast(error.code())

            else -> {
                viewState.showToast(error::class.java.name)
                viewState.showToast(R.string.error)
                error.printStackTrace()
            }
        }
    }

}
