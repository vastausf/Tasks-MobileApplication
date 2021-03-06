package com.vastausf.tasks.presentation.fragment.task

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
class TaskFragmentPresenter
@Inject
constructor(
    private val tasksTokenStore: TasksTokenStore,
    private val tasksApiClient: TasksApiClient
) : BaseFragmentPresenter<TaskFragmentView>() {

    lateinit var taskData: TaskDataFull

    var taskDataEdit = TaskDataEdit()

    var taskId = 0

    var canEditData = false
        get() {
            field = tasksTokenStore.userId == taskData.creatorId.id

            return field
        }

    var canEditStatus = false
        get() {
            field = tasksTokenStore.userId == taskData.creatorId.id || tasksTokenStore.userId == taskData.assignId.id

            return field
        }

    var loadState = false
        set(value) {
            field = value
            viewState.updateLoadState()
        }

    var editModeState = false
        set(value) {
            field = value

            if (!editModeState)
                editTaskData(taskId)

            viewState.updateEditState()
        }

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

        viewState.bindTaskData()
    }

    private fun onLoadTaskDataError(error: Throwable) {
        viewState.showToast(R.string.error)
        error.printStackTrace()
    }

    fun changeTaskStatus(newStatus: Int, comment: String) {
        loadState = true

        tasksApiClient
            .changeTaskStatus(taskId, newStatus, comment)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                loadState = false
            }
            .subscribe(this::onTaskStatusEditSuccess, this::onTaskStatusEditError)
            .unsubscribeOnDestroy()
    }

    private fun onTaskStatusEditSuccess(data: TaskStatusEditC) {
        loadTaskData()
    }

    private fun onTaskStatusEditError(error: Throwable) {
        viewState.showToast(R.string.error)
        error.printStackTrace()
    }

    private fun editTaskData(id: Int) {
        loadState = true

        tasksApiClient
            .editTask(id, taskDataEdit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                loadState = false
            }
            .subscribe(this::onEditTaskDataSuccess, this::onEditTaskDataError)
            .unsubscribeOnDestroy()
    }

    private fun onEditTaskDataSuccess(data: TaskEditC) {
        taskDataEdit = TaskDataEdit()
        loadTaskData()
    }

    private fun onEditTaskDataError(error: Throwable) {
        viewState.showToast(R.string.error)
        error.printStackTrace()
    }

}
