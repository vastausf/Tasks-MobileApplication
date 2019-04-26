package com.vastausf.tasks.presentation.fragment.main

import com.arellomobile.mvp.InjectViewState
import com.vastausf.tasks.R
import com.vastausf.tasks.TasksApplication
import com.vastausf.tasks.model.api.TasksApiClient
import com.vastausf.tasks.model.api.tasksApiData.ProjectDataSearch
import com.vastausf.tasks.model.api.tasksApiData.ProjectFindC
import com.vastausf.tasks.presentation.fragment.base.BaseFragmentPresenter
import com.vastausf.tasks.presentation.fragment.newProject.NewProjectFragment
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

    fun loadProjects(
        offset: Int,
        limit: Int,
        parameters: ProjectDataSearch = ProjectDataSearch(),
        clean: Boolean = true
    ) {
        viewState.projectsLoadStatus(true)

        tasksApiClient
            .getProjectList(offset, limit, parameters)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                viewState.projectsLoadStatus(false)
            }
            .subscribe(
                if (clean) this::onLoadProjectsSuccessClean else this::onLoadProjectsSuccess,
                this::onLoadProjectsError
            )
            .unsubscribeOnDestroy()
    }

    private fun onLoadProjectsSuccess(data: ProjectFindC) {
        viewState.bindProjectList(data.data, false)
    }

    private fun onLoadProjectsSuccessClean(data: ProjectFindC) {
        viewState.bindProjectList(data.data, true)
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

    fun onNewProjectClick() {
        viewState.launchFragment(NewProjectFragment())
    }

}
