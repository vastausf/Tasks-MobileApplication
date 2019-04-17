package com.vastausf.tasks.di.activity

import com.vastausf.tasks.di.application.ApplicationComponent
import com.vastausf.tasks.presentation.activity.main.MainActivity
import dagger.Component

@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)

}
