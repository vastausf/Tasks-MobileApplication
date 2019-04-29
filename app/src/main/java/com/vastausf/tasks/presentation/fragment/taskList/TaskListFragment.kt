package com.vastausf.tasks.presentation.fragment.taskList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vastausf.tasks.R
import com.vastausf.tasks.di.fragment.DaggerFragmentComponent
import com.vastausf.tasks.presentation.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_task_list.*
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)

        return view
    }

    override fun loadStatus(status: Boolean) {
        view?.apply {
            srlTasks.isRefreshing = status
        }
    }

    override fun bindTaskList() {
        view?.apply {
            rvTasks.adapter?.notifyDataSetChanged()
        }
    }

}