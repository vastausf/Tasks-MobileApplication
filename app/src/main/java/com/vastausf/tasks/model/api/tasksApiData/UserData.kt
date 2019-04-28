package com.vastausf.tasks.model.api.tasksApiData

data class UserFindS(
    val offset: Int,
    val limit: Int,
    val parameters: UserDataSearch
)

data class UserFindC(
    val data: List<UserData>
)


data class UserData(
    val id: Int,
    val email: String,
    val lastName: String,
    val firstName: String,
    val middleName: String
)

data class UserDataFull(
    val id: Int,
    val login: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val email: String
)

data class UserDataSearch(
    val ids: List<Int>? = null,
    val email: String? = null,
    val name: String? = null
)
