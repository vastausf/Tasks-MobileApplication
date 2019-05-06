package com.vastausf.tasks.presentation.fragment.project

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vastausf.tasks.R
import com.vastausf.tasks.di.fragment.DaggerFragmentComponent
import com.vastausf.tasks.model.api.tasksApiData.ProjectDataEdit
import com.vastausf.tasks.presentation.fragment.base.BaseFragment
import com.vastausf.tasks.presentation.fragment.projectEdit.ProjectEditFragment
import kotlinx.android.synthetic.main.fragment_project.view.*
import javax.inject.Inject

class ProjectFragment : BaseFragment(), ProjectFragmentView {
    @Inject
    @get:ProvidePresenter
    @field:InjectPresenter
    lateinit var presenter: ProjectFragmentPresenter

    var projectId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_project, container, false)

        view.apply {
            activity?.setActionBar(tProject)
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

    override fun bindProjectData() {
        view?.apply {
            bDocuments.setOnClickListener {
                AlertDialog
                    .Builder(context)
                    .setItems(presenter.projectData.documents.toTypedArray()) { _, which ->
                        val document = presenter.projectData.documents[which]

                        if (document.startsWith("http") || document.startsWith("https")) {
                            Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse(document)

                                startActivity(this)
                            }
                        } else {
                            showToast(R.string.error)
                        }
                    }
                    .create()
                    .show()
            }

            bSpecification.setOnClickListener {
                val specification = presenter.projectData.specification

                if (specification.startsWith("http") || specification.startsWith("https")) {
                    Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(specification)

                        startActivity(this)
                    }
                } else {
                    showToast(R.string.error)
                }
            }

            bCredentials.setOnClickListener {
                AlertDialog
                    .Builder(context)
                    .setItems(presenter.projectData.credentials.map {
                        "${it.firstName} ${it.lastName}"
                    }.toTypedArray(), null)
                    .create()
                    .show()
            }

            etTitle.setText(presenter.projectData.title)

            etDescription.setText(presenter.projectData.description)

            srlProject.setOnRefreshListener {
                presenter.loadProjectData()
            }

            bEdit.setOnClickListener {
                launchFragment(ProjectEditFragment(), bundle = Bundle().apply {
                    putInt("projectId", projectId)
                })
            }
        }
    }

    override fun updateLoadStatus() {
        val status = presenter.loadStatus

        view?.apply {
            srlProject.isRefreshing = status
        }
    }

    override fun onStart() {
        super.onStart()

        presenter.projectId = projectId
        presenter.loadProjectData()
    }

}
