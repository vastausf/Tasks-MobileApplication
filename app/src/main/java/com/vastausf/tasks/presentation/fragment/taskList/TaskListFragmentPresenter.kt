package com.vastausf.tasks.presentation.fragment.taskList

import com.arellomobile.mvp.InjectViewState
import com.vastausf.tasks.R
import com.vastausf.tasks.model.api.TasksApiClient
import com.vastausf.tasks.model.api.tasksApiData.TaskDataFull
import com.vastausf.tasks.model.api.tasksApiData.TaskDataSearch
import com.vastausf.tasks.model.api.tasksApiData.TaskFindC
import com.vastausf.tasks.presentation.fragment.base.BaseFragmentPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class TaskListFragmentPresenter
@Inject
constructor(
    private val tasksApiClient: TasksApiClient
) : BaseFragmentPresenter<TaskListFragmentView>() {

    var projectId: Int = 0

    var taskList = mutableListOf<TaskDataFull>()

    fun loadTaskList() {
        viewState.loadStatus(true)

        tasksApiClient
            .getTaskList(TaskDataSearch(ids = listOf(projectId)))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                viewState.loadStatus(false)
            }
            .subscribe(this::onLoadTaskListSuccess, this::onLoadTaskListError)
            .unsubscribeOnDestroy()
    }

    private fun onLoadTaskListSuccess(data: TaskFindC) {
        taskList.clear()
        taskList.addAll(data.data)

        viewState.bindTaskList()
    }

    private fun onLoadTaskListError(error: Throwable) {
        viewState.showToast(R.string.error)
        error.printStackTrace()
    }

}
