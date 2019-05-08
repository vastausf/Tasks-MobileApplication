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
    private val tasksTokenStore: TasksTokenStore
) : BaseActivityPresenter<MainActivityView>() {
    val haveAccessToken: Boolean
        get() {
            return tasksTokenStore.accessToken.isNotEmpty()
        }

    fun onCreate() {
        if (haveAccessToken) {
            viewState.launchFragment(MainFragment(), true)
        } else {
            viewState.launchFragment(LoginFragment(), true)
        }
    }

    fun onExitAccount() {
        tasksTokenStore.clearTokens()

        viewState.launchFragment(LoginFragment(), true)
    }

}
