package com.vastausf.tasks.presentation.fragment.base

import com.arellomobile.mvp.MvpPresenter
import com.vastausf.tasks.model.api.TasksTokenStore
import com.vastausf.tasks.presentation.fragment.login.LoginFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import javax.inject.Inject

abstract class BaseFragmentPresenter<T : BaseFragmentView> : MvpPresenter<T>() {
    private val compositeDisposable = CompositeDisposable()

    protected fun Disposable.unsubscribeOnDestroy() =
        compositeDisposable.add(this)

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }
}
