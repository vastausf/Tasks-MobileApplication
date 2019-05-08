package com.vastausf.tasks.presentation.fragment.taskEdit

import com.arellomobile.mvp.InjectViewState
import com.vastausf.tasks.R
import com.vastausf.tasks.model.api.TasksApiClient
import com.vastausf.tasks.model.api.tasksApiData.*
import com.vastausf.tasks.presentation.fragment.base.BaseFragmentPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class TaskEditFragmentPresenter
@Inject
constructor(
    private val tasksApiClient: TasksApiClient
) : BaseFragmentPresenter<TaskEditFragmentView>() {

    var taskId: Int = 0

    var taskDataEdit = TaskDataEdit()

    var loadState = false
        set(value) {
            field = value

            viewState.updateLoadState()
        }

    lateinit var taskData: TaskDataFull

    fun loadTaskData() {
        loadState = true

        tasksApiClient
            .getTaskData(TaskDataSearch(
                ids = listOf(taskId)
            ))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                loadState = false
            }
            .subscribe(this::onLoadTaskDataSuccess, this::onLoadTaskDataError)
            .unsubscribeOnDestroy()
    }

    private fun onLoadTaskDataSuccess(data: TaskFindC) {
        taskData = data.data.first()

        taskDataEdit = TaskDataEdit(
            taskData.title,
            taskData.assignId.id,
            taskData.description
        )

        viewState.bindTaskData()
    }

    private fun onLoadTaskDataError(error: Throwable) {
        viewState.showToast(R.string.error)
        error.printStackTrace()
    }

    fun editTaskData() {
        loadState = true

        tasksApiClient
            .editTask(taskId, taskDataEdit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                loadState = false
            }
            .subscribe(this::onEditTaskDataSuccess, this::onEditTaskDataError)
            .unsubscribeOnDestroy()
    }

    private fun onEditTaskDataSuccess(data: TaskEditC) {
        viewState.showToast(R.string.success)
        viewState.goBack()
    }

    private fun onEditTaskDataError(error: Throwable) {
        viewState.showToast(R.string.error)
        error.printStackTrace()
    }

}