package com.vastausf.tasks.di.application

import android.content.SharedPreferences
import com.vastausf.tasks.TasksApplication
import com.vastausf.tasks.model.api.TasksApiService
import com.vastausf.tasks.model.api.TasksTokenStore
import dagger.Component

@Component(
    modules = [ApplicationModule::class]
)
interface ApplicationComponent {

    val tasksApplication: TasksApplication

    val tasksSharedPreferences: SharedPreferences

    val tasksApiService: TasksApiService

    val tasksTokenStore: TasksTokenStore

}
