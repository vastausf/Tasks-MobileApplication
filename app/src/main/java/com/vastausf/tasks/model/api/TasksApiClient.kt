package com.vastausf.tasks.model.api

import com.vastausf.tasks.model.api.tasksApiData.*
import io.reactivex.Single
import javax.inject.Inject

class TasksApiClient
@Inject
constructor(
    private val tasksApiService: TasksApiService,
    private val tasksTokenStore: TasksTokenStore
) {

    private val contentType = "application/json"

    fun getToken(login: String, password: String): Single<AuthTokenGetC> {
        return tasksApiService.authTokenGet(
            contentType,
            AuthTokenGetS(
                login,
                password
            )
        )
    }

    fun getProjectList(parameters: ProjectDataSearch): Single<ProjectFindC> {
        return tasksApiService.projectFind(
            contentType,
            tasksTokenStore.accessToken,
            ProjectFindS(
                parameters
            )
        )
    }

    fun getProjectFullData(projectId: Int): Single<ProjectDataFullC> {
        return tasksApiService.projectFullData(
            contentType,
            tasksTokenStore.accessToken,
            ProjectDataFullS(
                projectId
            )
        )
    }

    fun createNewProject(data: ProjectDataCreate): Single<ProjectNewC> {
        return tasksApiService.projectNew(
            contentType,
            tasksTokenStore.accessToken,
            ProjectNewS(data)
        )
    }

    fun editProject(id: Int, data: ProjectDataEdit): Single<ProjectEditC> {
        return tasksApiService.projectEdit(
            contentType,
            tasksTokenStore.accessToken,
            ProjectEditS(id, data)
        )
    }

    fun getUserList(parameters: UserDataSearch): Single<UserFindC> {
        return tasksApiService.userFind(
            contentType,
            tasksTokenStore.accessToken,
            UserFindS(
                parameters
            )
        )
    }

    fun getTaskList(parameters: TaskDataSearch): Single<TaskFindC> {
        return tasksApiService.taskFind(
            contentType,
            tasksTokenStore.accessToken,
            TaskFindS(
                parameters
            )
        )
    }

    fun createNewTask(data: TaskDataCreate): Single<TaskNewC> {
        return tasksApiService.taskNew(
            contentType,
            tasksTokenStore.accessToken,
            TaskNewS(data)
        )
    }

    fun changeTaskStatus(id: Int, newStatus: Int, comment: String): Single<TaskStatusEditC> {
        return tasksApiService.taskStatusEdit(
            contentType,
            tasksTokenStore.accessToken,
            TaskStatusEditS(id, newStatus, comment)
        )
    }

    fun editTask(id: Int, data: TaskDataEdit): Single<TaskEditC> {
        return tasksApiService.taskEdit(
            contentType,
            tasksTokenStore.accessToken,
            TaskEditS(id, data)
        )
    }

    fun getTaskData(parameters: TaskDataSearch): Single<TaskFindC> {
        return tasksApiService.taskFind(
            contentType,
            tasksTokenStore.accessToken,
            TaskFindS(
                parameters
            )
        )
    }

}