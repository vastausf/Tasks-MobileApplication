package com.vastausf.tasks.model.api.tasksApiData

data class ProjectNewS(
    val data: ProjectDataCreate
)

data class ProjectNewC(
    val data: ProjectDataShort
)

data class ProjectEditS(
    val id: Int,
    val newData: ProjectDataEdit
)

data class ProjectEditC(
    val data: ProjectDataShort
)

data class ProjectFindS(
    val offset: Int,
    val limit: Int,
    val parameters: ProjectDataSearch
)

data class ProjectFindC(
    val data: List<ProjectDataShort>
)

data class ProjectDataFullS(
    val projectId: Int
)

data class ProjectDataFullC(
    val data: ProjectDataFull
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
    var id: Int,
    var title: String,
    var description: String,
    var specification: String,
    var documents: List<String>,
    var credentials: List<UserData>,
    var created: Int,
    var tasks: List<TaskDataFull>
)

data class ProjectDataShort(
    val id: Int,
    val title: String,
    val description: String,
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
