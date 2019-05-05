package com.vastausf.tasks.presentation.fragment.projectList

import android.app.AlertDialog
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
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
import com.vastausf.tasks.presentation.adapter.ProjectsRecyclerView
import com.vastausf.tasks.presentation.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.bottom_sheet_project_search.view.*
import kotlinx.android.synthetic.main.fragment_project_list.view.*
import javax.inject.Inject

class ProjectListFragment : BaseFragment(), ProjectListFragmentView, ProjectsRecyclerView.ProjectListener {

    @Inject
    @get:ProvidePresenter
    @field:InjectPresenter
    lateinit var presenter: ProjectListFragmentPresenter

    var listener: ProjectListListener? = null

    interface ProjectListListener {

        fun onProjectClick(fragment: ProjectListFragment, projectData: ProjectDataShort)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_project_list, container, false)

        view.apply {
            rvProjects.let {
                it.adapter = ProjectsRecyclerView(this@ProjectListFragment, presenter.projectList)
                it.layoutManager = LinearLayoutManager(this.context)
            }

            srlProjectList.setOnRefreshListener {
                presenter.loadProjects()
            }

            bSearchProject.setOnClickListener {
                BottomSheetBehavior.from(llBottomSearchView).state = BottomSheetBehavior.STATE_EXPANDED
            }

            bCreateProject.setOnClickListener {
                val projectTitle = EditText(ContextThemeWrapper(context, R.style.EditText_WithoutUnderline)).apply {
                    inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                    hint = getString(R.string.title)
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    setBackgroundColor(ContextCompat.getColor(context, R.color.colorTransparent))
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
                    .setPositiveButton(getString(R.string.create)) { _, _ ->
                        if (projectTitle.text.toString().isNotBlank())
                            presenter.createProject(projectTitle.text.toString())
                        else
                            showToast(R.string.title_cant_be_empty)
                    }
                    .create()
                    .show()
            }

            llBottomSearchView.apply {
                bClearSearch.setOnClickListener {
                    etProjectTitleSearch.setText("")
                    etProjectDescriptionSearch.setText("")

                    BottomSheetBehavior.from(llBottomSearchView).state = BottomSheetBehavior.STATE_HIDDEN
                }

                etProjectTitleSearch.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        presenter.projectSearch.title = s.toString()

                        presenter.loadProjects()
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    }
                })

                etProjectDescriptionSearch.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        presenter.projectSearch.description = s.toString()

                        presenter.loadProjects()
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    }
                })
            }

            BottomSheetBehavior.from(llBottomSearchView).state = BottomSheetBehavior.STATE_HIDDEN
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

    override fun bindProjectList() {
        view?.rvProjects?.adapter?.notifyDataSetChanged()
    }

    override fun onProjectClick(projectData: ProjectDataShort) {
        listener?.onProjectClick(this@ProjectListFragment, projectData)
    }

    override fun onStart() {
        super.onStart()

        presenter.loadProjects()
    }

    override fun updateLoadState() {
        val state = presenter.loadState

        view?.apply {
            srlProjectList.isRefreshing = state
        }
    }

}
