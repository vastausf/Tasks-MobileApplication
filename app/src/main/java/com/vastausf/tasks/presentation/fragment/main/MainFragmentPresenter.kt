package com.vastausf.tasks.presentation.fragment.main

import com.arellomobile.mvp.InjectViewState
import com.vastausf.tasks.R
import com.vastausf.tasks.TasksApplication
import com.vastausf.tasks.model.api.TasksApiClient
import com.vastausf.tasks.model.api.tasksApiData.*
import com.vastausf.tasks.presentation.fragment.base.BaseFragmentPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject

@InjectViewState
class MainFragmentPresenter
@Inject
constructor(
    private val tasksApplication: TasksApplication,
    private val tasksApiClient: TasksApiClient
) : BaseFragmentPresenter<MainFragmentView>() {

}
