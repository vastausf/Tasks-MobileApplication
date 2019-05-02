package com.vastausf.tasks.presentation.fragment.userList

import com.arellomobile.mvp.InjectViewState
import com.vastausf.tasks.R
import com.vastausf.tasks.model.api.TasksApiClient
import com.vastausf.tasks.model.api.tasksApiData.UserData
import com.vastausf.tasks.model.api.tasksApiData.UserDataSearch
import com.vastausf.tasks.model.api.tasksApiData.UserFindC
import com.vastausf.tasks.presentation.fragment.base.BaseFragmentPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class UserListFragmentPresenter
@Inject
constructor(
    private val tasksApiClient: TasksApiClient
): BaseFragmentPresenter<UserListFragmentView>() {
    val userDataSearch = UserDataSearch()

    var loadState = false
        set(value) {
            field = value
            viewState.updateLoadState()
        }

    val userList = mutableListOf<UserData>()

    fun loadUserList() {
        loadState = true

        tasksApiClient
            .getUserList(userDataSearch)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                loadState = false
            }
            .subscribe(this::onLoadUserListSuccess, this::onLoadUserListError)
            .unsubscribeOnDestroy()
    }

    private fun onLoadUserListSuccess(data: UserFindC) {
        userList.clear()
        userList.addAll(data.data)

        viewState.updateUserList()
    }

    private fun onLoadUserListError(error: Throwable) {
        viewState.showToast(R.string.error)
        error.printStackTrace()
    }
}