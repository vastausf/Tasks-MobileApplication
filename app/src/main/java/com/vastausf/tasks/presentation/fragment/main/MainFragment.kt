package com.vastausf.tasks.presentation.fragment.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vastausf.tasks.R
import com.vastausf.tasks.di.fragment.DaggerFragmentComponent
import com.vastausf.tasks.model.api.tasksApiData.ProjectDataShort
import com.vastausf.tasks.presentation.adapter.ProjectsAdapter
import com.vastausf.tasks.presentation.fragment.base.BaseFragment
import com.vastausf.tasks.presentation.fragment.project.ProjectFragment
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import javax.inject.Inject

class MainFragment : BaseFragment(), MainFragmentView, ProjectsAdapter.ProjectListener {

    @Inject
    @get:ProvidePresenter
    @field:InjectPresenter
    lateinit var presenter: MainFragmentPresenter

    private val projectList = mutableListOf<ProjectDataShort>()

    override fun bindProjectList(data: List<ProjectDataShort>, clean: Boolean) {
        view?.rvProjects?.adapter?.let {
            val itemCount = it.itemCount

            if (clean) {
                projectList.clear()
                projectList.addAll(data)
                it.notifyDataSetChanged()
            } else {
                projectList.addAll(data)
                it.notifyItemRangeChanged(itemCount - 1, data.size)
            }

            view?.tvProjectsPlaceholder?.visibility = if (it.itemCount == 0)
                View.VISIBLE
            else
                View.GONE

        }
    }

    override fun onLoadLast() {
        rvProjects.adapter?.let {
            presenter.loadProjects(it.itemCount, resources.getInteger(R.integer.load_items), clean = false)
        }
    }

    override fun onProjectClick(projectData: ProjectDataShort) {
        launchFragment(ProjectFragment(), bundle = Bundle().apply {
            putInt("projectId", projectData.id)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        view.rvProjects.let {
            it.adapter = ProjectsAdapter(this, projectList)
            it.layoutManager = LinearLayoutManager(this.context)
        }

        view.srlProjects.setOnRefreshListener {
            presenter.loadProjects(0, resources.getInteger(R.integer.load_items))
        }

        view.fabNewProject.setOnClickListener {
            presenter.onNewProjectClick()
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

        presenter.loadProjects(0, resources.getInteger(R.integer.load_items))
    }

    override fun projectsLoadStatus(status: Boolean) {
        view?.apply {
            srlProjects.isRefreshing = status
        }
    }

}