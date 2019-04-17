package com.vastausf.tasks.presentation.fragment.base

import android.os.Bundle
import com.arellomobile.mvp.MvpView
import com.vastausf.tasks.R
import com.vastausf.tasks.presentation.activity.base.BaseActivity

interface BaseFragmentView : MvpView {

    fun launchActivity(activity: BaseActivity, finish: Boolean = false)

    fun launchFragment(fragment: BaseFragment, finish: Boolean = false, container: Int = R.id.fragmentContainer, bundle: Bundle? = null)

    fun replaceFragment(fragment: BaseFragment, finish: Boolean = false, container: Int = R.id.fragmentContainer)

    fun goBack()

    fun showToast(text: Any)

    fun showToast(text: Int)

    fun loadingProgress(state: Boolean)

}
