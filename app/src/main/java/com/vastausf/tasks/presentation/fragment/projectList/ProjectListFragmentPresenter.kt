package com.vastausf.tasks.presentation.fragment.projectList

import com.arellomobile.mvp.InjectViewState
import com.vastausf.tasks.R
import com.vastausf.tasks.model.api.TasksApiClient
import com.vastausf.tasks.model.api.tasksApiData.*
import com.vastausf.tasks.presentation.fragment.base.BaseFragmentPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject

@InjectViewState
class ProjectListFragmentPresenter
@Inject
constructor(
    private val tasksApiClient: TasksApiClient
) : BaseFragmentPresenter<ProjectListFragmentView>() {
    val projectSearch = ProjectDataSearch()

    var loadState = false
        set(value) {
            field = value
            viewState.updateLoadState()
        }

    val projectList = mutableListOf<ProjectDataShort>()

    fun loadProjects() {
        loadState = true

        tasksApiClient
            .getProjectList(projectSearch)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                loadState = false
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

        viewState.bindProjectList()
    }

    private fun onLoadProjectsError(error: Throwable) {
        when (error) {
            is HttpException -> viewState.showToast(error.code().toString())
        }
    }

    fun createProject(title: String) {
        loadState = true

        tasksApiClient
            .createNewProject(ProjectDataCreate(title, "", "", listOf(), listOf()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                loadState = false
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
