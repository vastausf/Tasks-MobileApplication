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

    fun getProjectList(offset: Int, limit: Int, parameters: ProjectDataSearch): Single<ProjectFindC> {
        return tasksApiService.projectFind(
            contentType,
            tasksTokenStore.accessToken,
            ProjectFindS(
                offset,
                limit,
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

}