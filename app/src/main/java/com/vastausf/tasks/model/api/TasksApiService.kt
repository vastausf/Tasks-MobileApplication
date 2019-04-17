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
        @Body body: AuthTokenGetO
    ): Single<AuthTokenGetI>

    @POST("user/find")
    fun userFind(
        @Header("ContentType") contentType: String,
        @Header("AccessToken") accessToken: String,
        @Body body: UserFindO
    ): Single<UserFindI>

    @POST("project/new")
    fun projectNew(
        @Header("ContentType") contentType: String,
        @Header("AccessToken") accessToken: String,
        @Body body: ProjectNewO
    ): Single<ProjectNewI>

    @POST("project/edit")
    fun projectEdit(
        @Header("ContentType") contentType: String,
        @Header("AccessToken") accessToken: String,
        @Body body: ProjectEditO
    ): Single<ProjectEditI>

    @POST("project/find")
    fun projectFind(
        @Header("ContentType") contentType: String,
        @Header("AccessToken") accessToken: String,
        @Body body: ProjectFindO
    ): Single<ProjectFindI>

    @POST("task/new")
    fun taskNew(
        @Header("ContentType") contentType: String,
        @Header("AccessToken") accessToken: String,
        @Body body: TaskNewO
    ): Single<TaskNewI>

    @POST("task/edit")
    fun taskEdit(
        @Header("ContentType") contentType: String,
        @Header("AccessToken") accessToken: String,
        @Body body: TaskEditO
    ): Single<TaskEditI>

    @POST("task/status/edit")
    fun taskStatusEdit(
        @Header("ContentType") contentType: String,
        @Header("AccessToken") accessToken: String,
        @Body body: TaskStatusEditO
    ): Single<TaskStatusEditI>

}
