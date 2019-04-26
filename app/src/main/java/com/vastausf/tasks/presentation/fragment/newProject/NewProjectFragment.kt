package com.vastausf.tasks.presentation.fragment.newProject

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vastausf.tasks.R
import com.vastausf.tasks.di.fragment.DaggerFragmentComponent
import com.vastausf.tasks.model.api.tasksApiData.ProjectDataFull
import com.vastausf.tasks.presentation.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_new_project.view.*
import javax.inject.Inject

class NewProjectFragment : BaseFragment(), NewProjectView {

    @Inject
    @get:ProvidePresenter
    @field:InjectPresenter
    lateinit var presenter: NewProjectPresenter

    var projectData: ProjectDataFull? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_new_project, container, false)

        view.bDocuments.setOnClickListener {
            projectData?.apply {
                AlertDialog
                    .Builder(view.context)
                    .setItems(documents.toTypedArray()) { dialog, which ->
                        showToast(documents[which])
                    }
                    .setNeutralButton(getString(R.string.add)) { dialog, which ->

                    }
                    .create()
                    .show()
            }
        }

        return view
    }

    private fun onCreateClick() {

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