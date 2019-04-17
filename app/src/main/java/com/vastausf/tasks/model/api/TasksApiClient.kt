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

    fun getToken(login: String, password: String): Single<AuthTokenGetI> {
        return tasksApiService.authTokenGet(
            contentType,
            AuthTokenGetO(
                login,
                password
            )
        )
    }

    fun getProjectList(offset: Int, limit: Int, parameters: ProjectDataSearch): Single<ProjectFindI> {
        return tasksApiService.projectFind(
            contentType,
            tasksTokenStore.accessToken,
            ProjectFindO(
                offset,
                limit,
                parameters
            )
        )
    }

}