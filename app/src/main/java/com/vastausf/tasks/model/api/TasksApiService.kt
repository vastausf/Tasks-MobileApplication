package com.vastausf.tasks.model.api

import com.vastausf.tasks.model.api.tasksApiData.*
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TasksApiService {

    @POST("auth/token/get")
    fun authTokenGet(
        @Header("ContentType") contentType: String,
        @Body body: AuthTokenGetS
    ): Single<AuthTokenGetC>

    @POST("user/find")
    fun userFind(
        @Header("ContentType") contentType: String,
        @Header("AccessToken") accessToken: String,
        @Body body: UserFindS
    ): Single<UserFindC>

    @POST("project/new")
    fun projectNew(
        @Header("ContentType") contentType: String,
        @Header("AccessToken") accessToken: String,
        @Body body: ProjectNewS
    ): Single<ProjectNewC>

    @POST("project/edit")
    fun projectEdit(
        @Header("ContentType") contentType: String,
        @Header("AccessToken") accessToken: String,
        @Body body: ProjectEditS
    ): Single<ProjectEditC>

    @POST("project/find")
    fun projectFind(
        @Header("ContentType") contentType: String,
        @Header("AccessToken") accessToken: String,
        @Body body: ProjectFindS
    ): Single<ProjectFindC>

    @POST("project/fullData")
    fun projectFullData(
        @Header("ContentType") contentType: String,
        @Header("AccessToken") accessToken: String,
        @Body body: ProjectDataFullS
    ): Single<ProjectDataFullC>

    @POST("task/new")
    fun taskNew(
        @Header("ContentType") contentType: String,
        @Header("AccessToken") accessToken: String,
        @Body body: TaskNewS
    ): Single<TaskNewC>

    @POST("task/edit")
    fun taskEdit(
        @Header("ContentType") contentType: String,
        @Header("AccessToken") accessToken: String,
        @Body body: TaskEditS
    ): Single<TaskEditC>

    @POST("task/status/edit")
    fun taskStatusEdit(
        @Header("ContentType") contentType: String,
        @Header("AccessToken") accessToken: String,
        @Body body: TaskStatusEditS
    ): Single<TaskStatusEditC>

    @POST("task/find")
    fun taskFind(
        @Header("ContentType") contentType: String,
        @Header("AccessToken") accessToken: String,
        @Body body: TaskFindS
    ): Single<TaskFindC>

}
