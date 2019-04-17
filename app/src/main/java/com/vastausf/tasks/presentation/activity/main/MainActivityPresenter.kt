package com.vastausf.tasks.presentation.activity.main

import com.arellomobile.mvp.InjectViewState
import com.vastausf.tasks.TasksApplication
import com.vastausf.tasks.model.api.TasksTokenStore
import com.vastausf.tasks.presentation.activity.base.BaseActivityPresenter
import com.vastausf.tasks.presentation.fragment.login.LoginFragment
import com.vastausf.tasks.presentation.fragment.main.MainFragment
import javax.inject.Inject

@InjectViewState
class MainActivityPresenter
@Inject
constructor(
    private val tasksApplication: TasksApplication,
    private val tasksTokenStore: TasksTokenStore
) : BaseActivityPresenter<MainActivityView>() {

    fun onCreate() {
        if (tasksTokenStore.accessToken.isEmpty()) {
            viewState.launchFragment(LoginFragment(), true)
        } else {
            viewState.launchFragment(MainFragment(), true)
        }
    }

}
