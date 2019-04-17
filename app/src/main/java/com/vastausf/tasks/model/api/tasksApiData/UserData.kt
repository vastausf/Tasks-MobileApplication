package com.vastausf.tasks.model.api.tasksApiData

data class UserFindO(
    val offset: Int,
    val limit: Int,
    val parameters: UserDataSearch
)

data class UserFindI(
    val data: List<UserData>
)


data class UserData(
    val id: Int,
    val email: String,
    val lastName: String,
    val firstName: String,
    val middleName: String
)

data class UserDataSearch(
    val email: String? = null,
    val lastName: String? = null,
    val firstName: String? = null,
    val middleName: String? = null
)
