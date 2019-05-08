package com.vastausf.tasks.presentation.fragment.taskList

import com.arellomobile.mvp.InjectViewState
import com.vastausf.tasks.R
import com.vastausf.tasks.model.api.TasksApiClient
import com.vastausf.tasks.model.api.TasksTokenStore
import com.vastausf.tasks.model.api.tasksApiData.*
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
    val taskDataSearch = TaskDataSearch()

    var loadState = false
        set(value) {
            field = value
            viewState.updateLoadState()
        }

    var taskList = mutableListOf<TaskDataFull>()

    fun loadTaskList() {
        loadState = true

        tasksApiClient
            .getTaskList(taskDataSearch)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                loadState = false
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

    fun createTask(taskDataCreate: TaskDataCreate) {
        loadState = true

        tasksApiClient
            .createNewTask(taskDataCreate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                loadState = false
            }
            .subscribe(this::onCreateNewTaskSuccess, this::onCreateNewTaskError)
            .unsubscribeOnDestroy()
    }

    private fun onCreateNewTaskSuccess(data: TaskNewC) {
        loadTaskList()
    }

    private fun onCreateNewTaskError(error: Throwable) {
        viewState.showToast(R.string.error)
        error.printStackTrace()
    }

}
