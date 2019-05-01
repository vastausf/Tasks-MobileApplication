package com.vastausf.tasks.presentation.fragment.projectList

import android.app.AlertDialog
import android.app.Service
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vastausf.tasks.R
import com.vastausf.tasks.di.fragment.DaggerFragmentComponent
import com.vastausf.tasks.model.api.tasksApiData.ProjectDataShort
import com.vastausf.tasks.presentation.adapter.ProjectsRecyclerView
import com.vastausf.tasks.presentation.fragment.base.BaseFragment
import com.vastausf.tasks.presentation.fragment.project.ProjectFragment
import kotlinx.android.synthetic.main.fragment_project_list.view.*
import javax.inject.Inject

class ProjectListFragment : BaseFragment(), ProjectListFragmentView, ProjectsRecyclerView.ProjectListener {

    @Inject
    @get:ProvidePresenter
    @field:InjectPresenter
    lateinit var presenter: ProjectListFragmentPresenter

    override fun updateSearchState() {
        val state = presenter.searchState

        view?.apply {
            etProjectTitleSearch.isEnabled = state

            if (state) {
                bProjectSearch.visibility = View.GONE

                etProjectTitleSearch.requestFocus()

                (tasksApplication
                    .getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .showSoftInput(etProjectTitleSearch, InputMethodManager.SHOW_IMPLICIT)
            } else {
                bProjectSearch.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_project_list, container, false)

        view.apply {
            rvProjects.let {
                it.adapter = ProjectsRecyclerView(this@ProjectListFragment, presenter.projectList)
                it.layoutManager = LinearLayoutManager(this.context)
            }

            srlProjects.setOnRefreshListener {
                presenter.loadProjects()
            }

            fabNewProject.setOnClickListener {
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

            bProjectSearch.setOnClickListener {
                presenter.searchState = true
            }

            etProjectTitleSearch.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    presenter.projectDataSearch.title = s.toString()
                    presenter.loadProjects()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            etProjectTitleSearch.setOnKeyListener { _, _, event ->
                if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
                    presenter.searchState = false
                }

                return@setOnKeyListener true
            }
        }

        activity?.setActionBar(view.findViewById(R.id.tbProjectList))

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
        launchFragment(ProjectFragment(), bundle = Bundle().apply {
            putInt("projectId", projectData.id)
        })
    }

    override fun onStart() {
        super.onStart()

        presenter.loadProjects()
    }

    override fun updateLoadState() {
        val state = presenter.loadState

        view?.apply {
            srlProjects.isRefreshing = state
        }
    }

}
