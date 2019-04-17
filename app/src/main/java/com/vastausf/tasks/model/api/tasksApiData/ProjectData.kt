package com.vastausf.tasks.model.api.tasksApiData

data class ProjectNewO(
    val data: ProjectDataCreate
)

data class ProjectNewI(
    val data: ProjectDataFull
)

data class ProjectEditO(
    val id: Int,
    val newData: ProjectDataEdit
)

data class ProjectEditI(
    val data: ProjectDataFull
)

data class ProjectFindO(
    val offset: Int,
    val limit: Int,
    val parameters: ProjectDataSearch
)

data class ProjectFindI(
    val data: List<ProjectDataFull>
)

data class ProjectData(
    val title: String,
    val description: String,
    val specification: String,
    val credentials: List<Int>,
    val documents: List<String>
)

data class ProjectDataCreate(
    val title: String,
    val description: String,
    val specification: String,
    val credentials: List<Int>,
    val documents: List<String>
)

data class ProjectDataFull(
    val id: Int,
    val title: String,
    val description: String,
    val specification: String,
    val documents: List<String>,
    val credentials: List<UserData>,
    val created: Int
)

data class ProjectDataEdit(
    val title: String? = null,
    val description: String? = null,
    val specification: String? = null,
    val credentials: List<Int>? = null,
    val documents: List<String>? = null
)

data class ProjectDataSearch(
    val id: Int? = null,
    val title: String? = null,
    val description: String? = null
)
