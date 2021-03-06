package com.vastausf.tasks.model.api.tasksApiData

data class TaskNewS(
    val data: TaskDataCreate
)

data class TaskNewC(
    val data: TaskDataFull
)

data class TaskEditS(
    val id: Int,
    val newData: TaskDataEdit
)

data class TaskEditC(
    val newData: TaskDataFull
)

data class TaskStatusEditS(
    val id: Int,
    val newStatus: Int,
    val comment: String
)

data class TaskStatusEditC(
    val data: TaskDataFull
)

data class TaskFindS(
    val parameters: TaskDataSearch
)

data class TaskFindC(
    val data: List<TaskDataFull>
)


data class TaskData(
    val id: Int,
    val project: Int,
    val title: String,
    val status: Int,
    val creatorId: UserData,
    val assignId: UserData,
    val description: String,
    val documents: List<String>
)

data class TaskDataCreate(
    val project: Int,
    val title: String,
    val assigned: Int,
    val description: String,
    val documents: List<String>
)

data class TaskDataFull(
    val id: Int,
    val projectId: Int,
    val title: String,
    val status: Int,
    val creatorId: UserData,
    val assignId: UserData,
    val description: String,
    val time: Long,
    val documents: List<String>,
    val history: List<HistoryItem>
)

data class TaskDataShort(
    val id: Int,
    val title: String,
    val status: Int,
    val creatorId: UserData,
    val assignId: UserData,
    val description: String,
    val time: Long
)

data class TaskDataEdit(
    var title: String? = null,
    var assigned: Int? = null,
    var description: String? = null,
    var documents: List<String>? = null
)

data class TaskDataSearch(
    var ids: List<Int>? = null,
    var title: String? = null,
    var creatorId: List<Int>? = null,
    var assignId: List<Int>? = null,
    var description: String? = null,
    var projectId: List<Int>? = null
)

data class HistoryItem(
    val userId: Int,
    val statusFrom: Int,
    val statusTo: Int,
    val time: Long,
    val comment: String
)
