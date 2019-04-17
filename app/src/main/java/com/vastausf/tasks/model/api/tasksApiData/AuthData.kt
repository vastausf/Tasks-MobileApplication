package com.vastausf.tasks.model.api.tasksApiData

data class AuthTokenGetO(
    val login: String,
    val password: String
)

data class AuthTokenGetI(
    val token: String
)

data class AuthData(
    val login: String,
    val password: String
)
