package com.vastausf.tasks.presentation.fragment.main

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.content.ContextCompat.getColor
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
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
import java.awt.font.TextAttribute
import javax.inject.Inject

class MainFragment : BaseFragment(), MainFragmentView, ProjectsAdapter.ProjectListener {

    @Inject
    @get:ProvidePresenter
    @field:InjectPresenter
    lateinit var presenter: MainFragmentPresenter

    override fun bindProjectList(data: List<ProjectDataShort>) {
        view?.rvProjects?.adapter?.let {
            it.notifyDataSetChanged()

            view?.tvProjectsPlaceholder?.visibility = if (it.itemCount == 0)
                View.VISIBLE
            else
                View.GONE
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
            it.adapter = ProjectsAdapter(this, presenter.projectList)
            it.layoutManager = LinearLayoutManager(this.context)
        }

        view.srlProjects.setOnRefreshListener {
            presenter.loadProjects()
        }

        view.fabNewProject.setOnClickListener {
            val projectTitle = EditText(ContextThemeWrapper(context, R.style.EditText_WithoutUnderline)).apply {
                inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                hint = getString(R.string.title)
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                setBackgroundColor(getColor(context, R.color.colorTransparent))
                setPadding(
                    resources.getDimension(R.dimen.spacing_normal).toInt(),
                    resources.getDimension(R.dimen.spacing_normal).toInt(),
                    resources.getDimension(R.dimen.spacing_normal).toInt(),
                    resources.getDimension(R.dimen.spacing_normal).toInt()
                )
            }

            AlertDialog
                .Builder(context)
                .setView(projectTitle)
                .setPositiveButton(getString(R.string.create)) { dialog, which ->
                    if (projectTitle.text.toString().isNotBlank())
                        presenter.createProject(projectTitle.text.toString())
                    else
                        showToast(R.string.title_cant_be_empty)
                }
                .create()
                .show()
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

        presenter.loadProjects()
    }

    override fun projectsLoadStatus(status: Boolean) {
        view?.apply {
            srlProjects.isRefreshing = status
        }
    }

}