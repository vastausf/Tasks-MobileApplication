package com.vastausf.tasks

import android.app.Application
import com.vastausf.tasks.di.application.ApplicationComponent
import com.vastausf.tasks.di.application.ApplicationModule
import com.vastausf.tasks.di.application.DaggerApplicationComponent

class TasksApplication : Application() {

    lateinit var instance: TasksApplication

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        instance = this

        component = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

}