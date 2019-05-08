package com.vastausf.tasks.presentation.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vastausf.tasks.R
import com.vastausf.tasks.di.fragment.DaggerFragmentComponent
import com.vastausf.tasks.model.api.tasksApiData.ProjectDataShort
import com.vastausf.tasks.model.api.tasksApiData.TaskDataFull
import com.vastausf.tasks.presentation.adapter.MainViewPager
import com.vastausf.tasks.presentation.fragment.base.BaseFragment
import com.vastausf.tasks.presentation.fragment.projectInfo.ProjectInfoFragment
import com.vastausf.tasks.presentation.fragment.projectList.ProjectListFragment
import com.vastausf.tasks.presentation.fragment.task.TaskFragment
import com.vastausf.tasks.presentation.fragment.taskList.TaskListFragment
import kotlinx.android.synthetic.main.fragment_main.view.*
import javax.inject.Inject

class MainFragment : BaseFragment(), MainFragmentView, ProjectListFragment.ProjectListListener, TaskListFragment.TaskListListener {

    @Inject
    @get:ProvidePresenter
    @field:InjectPresenter
    lateinit var presenter: MainFragmentPresenter

    override fun onProjectClick(fragment: ProjectListFragment, projectData: ProjectDataShort) {

        launchFragment(ProjectInfoFragment(), bundle = Bundle().apply {
            putInt("projectId", projectData.id)
        })

    }

    override fun onTaskClick(fragment: TaskListFragment, taskData: TaskDataFull) {

        launchFragment(TaskFragment(), bundle = Bundle().apply {
            putInt("taskId", taskData.id)
        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        view.apply {
            vpMain.adapter = MainViewPager(childFragmentManager).apply {
                projectList.listener = this@MainFragment
                taskList.listener = this@MainFragment
            }
        }

        return view
    }

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