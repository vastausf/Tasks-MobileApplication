package com.vastausf.tasks.presentation.fragment.taskList

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vastausf.tasks.R
import com.vastausf.tasks.di.fragment.DaggerFragmentComponent
import com.vastausf.tasks.model.api.tasksApiData.TaskDataFull
import com.vastausf.tasks.presentation.adapter.TasksRecyclerView
import com.vastausf.tasks.presentation.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_task_list.view.*
import javax.inject.Inject

class TaskListFragment: BaseFragment(), TaskListFragmentView, TasksRecyclerView.TaskListener {

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

        view.apply {
            srlTasks.setOnRefreshListener {
                presenter.loadTaskList()
            }

            rvTasks.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = TasksRecyclerView(presenter.taskList).apply {
                    listener = this@TaskListFragment
                }
            }
        }

        return view
    }

    override fun onTaskClick(taskData: TaskDataFull) {
        showToast(taskData)
    }

    override fun loadStatus() {
        val state = presenter.loadState

        view?.apply {
            srlTasks.isRefreshing = state
        }
    }

    override fun bindTaskList() {
        view?.apply {
            rvTasks.adapter?.notifyDataSetChanged()
        }
    }

    override fun onStart() {
        super.onStart()

        presenter.loadTaskList()
    }

}