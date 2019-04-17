package com.vastausf.tasks.presentation.activity.base

import com.arellomobile.mvp.MvpView
import com.vastausf.tasks.presentation.fragment.base.BaseFragment

interface BaseActivityView : MvpView {

    fun launchActivity(activity: BaseActivity, finish: Boolean = false)

    fun launchFragment(fragment: BaseFragment, finish: Boolean = false)

    fun showToast(text: Any)

}
