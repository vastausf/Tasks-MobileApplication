package com.vastausf.tasks.presentation.fragment.taskList

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vastausf.tasks.di.fragment.DaggerFragmentComponent
import com.vastausf.tasks.presentation.fragment.base.BaseFragment
import javax.inject.Inject

class TaskListFragment: BaseFragment(), TaskListFragmentView {
    @Inject
    @get:ProvidePresenter
    @field:InjectPresenter
    lateinit var presenter: TaskListFragmentPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            DaggerFragmentComponent
                .builder()
                .applicationComponent(tasksApplication.component)
                .build()
                .inject(this)
        }
        super.onCreate(savedInstanceState)
    }

}