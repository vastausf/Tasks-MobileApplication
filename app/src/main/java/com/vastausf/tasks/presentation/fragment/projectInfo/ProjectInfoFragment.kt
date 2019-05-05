package com.vastausf.tasks.presentation.fragment.projectInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vastausf.tasks.R
import com.vastausf.tasks.di.fragment.DaggerFragmentComponent
import com.vastausf.tasks.model.api.tasksApiData.TaskDataFull
import com.vastausf.tasks.presentation.adapter.ProjectViewPagerAdapter
import com.vastausf.tasks.presentation.fragment.base.BaseFragment
import com.vastausf.tasks.presentation.fragment.task.TaskFragment
import com.vastausf.tasks.presentation.fragment.taskList.TaskListFragment
import kotlinx.android.synthetic.main.fragment_project_page.view.*
import javax.inject.Inject

class ProjectInfoFragment: BaseFragment(), ProjectInfoFragmentView, TaskListFragment.TaskListListener {

    @Inject
    @get:ProvidePresenter
    @field:InjectPresenter
    lateinit var presenter: ProjectInfoFragmentPresenter

    override fun onTaskClick(fragment: TaskListFragment, taskData: TaskDataFull) {

        launchFragment(TaskFragment(), bundle = Bundle().apply {
            putInt("taskId", taskData.id)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_project_page, container, false)

        view?.apply {
            val projectViewPagerAdapter = ProjectViewPagerAdapter(childFragmentManager).apply {
                projectTasks.listener = this@ProjectInfoFragment
            }

            vpProject.adapter = projectViewPagerAdapter.apply {
                val projectId = arguments?.getInt("projectId") ?: 0

                project.projectId = projectId
                projectTasks.projectId = projectId
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
