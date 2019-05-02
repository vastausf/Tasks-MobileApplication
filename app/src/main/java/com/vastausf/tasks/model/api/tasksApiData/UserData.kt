package com.vastausf.tasks.model.api.tasksApiData

data class UserFindS(
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
    var ids: List<Int>? = null,
    var email: String? = null,
    var lastName: String? = null,
    var firstName: String? = null,
    var middleName: String? = null
)
