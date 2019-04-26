package com.vastausf.tasks.presentation.fragment.project

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vastausf.tasks.R
import com.vastausf.tasks.di.fragment.DaggerFragmentComponent
import com.vastausf.tasks.model.api.tasksApiData.ProjectDataFull
import com.vastausf.tasks.model.api.tasksApiData.TaskData
import com.vastausf.tasks.model.api.tasksApiData.TaskDataFull
import com.vastausf.tasks.model.api.tasksApiData.UserData
import com.vastausf.tasks.presentation.adapter.ProjectPagerAdapter
import com.vastausf.tasks.presentation.adapter.TasksAdapter
import com.vastausf.tasks.presentation.adapter.UsersAdapter
import com.vastausf.tasks.presentation.adapter.pages.FragmentProjectMain
import com.vastausf.tasks.presentation.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_project.view.*
import kotlinx.android.synthetic.main.fragment_project_main.*
import kotlinx.android.synthetic.main.fragment_project_main.view.*
import javax.inject.Inject

class ProjectFragment : BaseFragment(), ProjectFragmentView, UsersAdapter.UserListener, FragmentProjectMain.ProjectListener, TasksAdapter.TaskListener {

    @Inject
    @get:ProvidePresenter
    @field:InjectPresenter
    lateinit var presenter: ProjectFragmentPresenter

    private var projectData: ProjectDataFull? = null

    private lateinit var projectAdapter: ProjectPagerAdapter

    override fun projectLoadStatus(status: Boolean) {
        view?.apply {
            projectAdapter.main.view?.srlProject?.isRefreshing = status
        }
    }

    override fun onUserClick(userData: UserData) {
        showToast(userData)
    }

    override fun onTaskClick(taskData: TaskDataFull) {
        showToast(taskData)
    }

    override fun onReload() {
        presenter.loadProjectData(arguments?.getInt("projectId"))
    }

    override fun onDocumentsClick() {
        projectData?.apply {
            AlertDialog
                .Builder(this@ProjectFragment.context)
                .setItems(documents.toTypedArray()) { dialog, which ->
                    showToast(documents[which])
                }
                .create()
                .show()
        }
    }

    override fun onSpecificationClick() {

    }

    override fun bindProjectData(projectDataFull: ProjectDataFull) {
        projectData = projectDataFull
        view?.apply {
            projectAdapter.main.apply {
                tvTitle?.apply {
                    background = null
                    text = projectDataFull.title
                }

                tvDescription?.apply {
                    setBackgroundColor(context.getColor(R.color.colorTransparent))
                    text = projectDataFull.description
                }
            }

            projectAdapter.users.apply {
                bindUsers(projectDataFull.credentials)
            }

            projectAdapter.tasks.apply {
                bindTasks(projectDataFull.tasks)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_project, container, false)
        projectAdapter = ProjectPagerAdapter(childFragmentManager)

        view.apply {
            vpProject.apply {
                adapter = projectAdapter
                currentItem = 1
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

    override fun onStart() {
        super.onStart()

        presenter.loadProjectData(arguments?.getInt("projectId"))
    }

}
