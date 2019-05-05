package com.vastausf.tasks.model.api.tasksApiData

import com.vastausf.tasks.R

enum class TaskStatus(
    val title: Int
) {
    CREATED(R.string.created),
    ASSIGNED(R.string.assigned),
    IN_PROGRESS(R.string.in_progress),
    IN_PAUSE(R.string.in_pause),
    CHECK(R.string.check),
    COMPLETED(R.string.completed),
    CLOSED(R.string.closed),
    REJECTED(R.string.rejected);

    companion object {
        fun fromInt(index: Int) =
            values().find { it.ordinal == index }?.title ?: R.string.unexpected_status
    }
}