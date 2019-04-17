package com.vastausf.tasks.presentation.fragment.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vastausf.tasks.R
import com.vastausf.tasks.di.fragment.DaggerFragmentComponent
import com.vastausf.tasks.model.api.tasksApiData.ProjectDataFull
import com.vastausf.tasks.model.api.tasksApiData.UserData
import com.vastausf.tasks.presentation.adapter.ProjectPagerAdapter
import com.vastausf.tasks.presentation.adapter.ProjectsAdapter
import com.vastausf.tasks.presentation.adapter.UsersAdapter
import com.vastausf.tasks.presentation.adapter.pages.FragmentProjectMain
import com.vastausf.tasks.presentation.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_project.view.*
import kotlinx.android.synthetic.main.fragment_project_main.*
import kotlinx.android.synthetic.main.fragment_project_main.view.*
import javax.inject.Inject

class ProjectFragment : BaseFragment(), ProjectFragmentView, UsersAdapter.UserListener, FragmentProjectMain.ProjectListener{

    @Inject
    @get:ProvidePresenter
    @field:InjectPresenter
    lateinit var presenter: ProjectFragmentPresenter

    private lateinit var projectAdapter: ProjectPagerAdapter

    override fun projectLoadStatus(status: Boolean) {
        view?.apply {
            projectAdapter.main.view?.srlProject?.isRefreshing = status
        }
    }

    override fun onUserClick(userData: UserData) {
        showToast(userData)
    }

    override fun onReload() {
        presenter.loadProjectData(arguments?.getInt("projectId"))
    }

    override fun bindProjectData(projectDataFull: ProjectDataFull) {
        view?.apply {
            projectAdapter.main.tvTitle?.apply {
                background = null
                text = projectDataFull.title
            }

            projectAdapter.main.tvDescription?.apply {
                setBackgroundColor(context.getColor(R.color.colorTransparent))
                text = projectDataFull.description
            }

            projectAdapter.users.apply {
                bindUsers(this@ProjectFragment, projectDataFull.credentials)
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
