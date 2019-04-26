package com.vastausf.tasks.model.api.tasksApiData

data class AuthTokenGetS(
    val login: String,
    val password: String
)

data class AuthTokenGetC(
    val token: String
)

data class AuthData(
    val login: String,
    val password: String
)
