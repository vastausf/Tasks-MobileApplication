package com.vastausf.tasks.presentation.fragment.login

import com.arellomobile.mvp.InjectViewState
import com.vastausf.tasks.R
import com.vastausf.tasks.model.TasksDataStore
import com.vastausf.tasks.model.api.TasksApiClient
import com.vastausf.tasks.model.api.TasksTokenStore
import com.vastausf.tasks.model.api.tasksApiData.AuthTokenGetI
import com.vastausf.tasks.presentation.fragment.base.BaseFragmentPresenter
import com.vastausf.tasks.presentation.fragment.main.MainFragment
import com.vastausf.tasks.utils.HttpStatusCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject

@InjectViewState
class LoginFragmentPresenter
@Inject
constructor(
    private val tasksApiClient: TasksApiClient,
    private val tasksTokenStore: TasksTokenStore,
    private val tasksDataStore: TasksDataStore
) : BaseFragmentPresenter<LoginFragmentView>() {

    fun onCreate() {
        viewState.bindData(tasksDataStore.login)
    }

    fun onLogin(login: String, password: String) {
        if (login.isNotEmpty() && password.isNotEmpty()) {
            tasksDataStore.login = login

            tasksApiClient
                .getToken(login, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::onLoginSuccess, ::onLoginError)
                .unsubscribeOnDestroy()
        } else {
            viewState.showToast(R.string.incorrect_login_or_password)
        }
    }

    private fun onLoginSuccess(data: AuthTokenGetI) {
        tasksTokenStore.accessToken = data.token

        viewState.launchFragment(MainFragment())

        viewState.showToast(data.token)
    }

    private fun onLoginError(error: Throwable) {
        when (error) {
            is HttpException -> when (error.code()) {
                HttpStatusCode.Unauthorized ->
                    viewState.showToast(R.string.incorrect_login_or_password)

                else ->
                    viewState.showToast(R.string.error)
            }

            else ->
                viewState.showToast(R.string.error)
        }
    }

}
