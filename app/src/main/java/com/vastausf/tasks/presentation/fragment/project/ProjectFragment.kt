package com.vastausf.tasks.presentation.fragment.project

import android.app.AlertDialog
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
import kotlinx.android.synthetic.main.fragment_project.view.*
import javax.inject.Inject

class ProjectFragment : BaseFragment(), ProjectFragmentView {
    @Inject
    @get:ProvidePresenter
    @field:InjectPresenter
    lateinit var presenter: ProjectFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter.projectId = arguments?.getInt("projectId") ?: 0

        val view = inflater.inflate(R.layout.fragment_project, container, false)

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
                    .setItems(presenter.projectData.documents.toTypedArray()) { dialog, which ->
                        showToast(presenter.projectData.documents[which])
                    }
                    .create()
                    .show()
            }

            bCredentials.setOnClickListener {
                AlertDialog
                    .Builder(context)
                    .setItems(presenter.projectData.credentials.map {
                        "${it.firstName} ${it.lastName}"
                    }.toTypedArray()) { dialog, which ->
                        showToast(presenter.projectData.credentials[which])
                    }
                    .create()
                    .show()
            }

            etTitle.setText(presenter.projectData.title)

            etDescription.setText(presenter.projectData.description)

            etSpecification.setText(presenter.projectData.specification)

            srlProject.setOnRefreshListener {
                presenter.loadProjectData()
            }

            fabEditProject.setOnClickListener {
                if (etTitle.text.isNotBlank()) {
                    presenter.onEditClick(
                        ProjectDataEdit(
                            etTitle.text.toString(),
                            etDescription.text.toString(),
                            etSpecification.text.toString()
                        )
                    )
                } else {
                    showToast(R.string.title_cant_be_empty)
                }
            }
        }
    }

    override fun updateEditMode() {
        val status = presenter.editMode

        view?.apply {
            etTitle.isEnabled = status
            etDescription.isEnabled = status
            etSpecification.isEnabled = status
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

        presenter.loadProjectData()
    }

}
