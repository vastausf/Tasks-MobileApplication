package com.vastausf.tasks.presentation.fragment.taskList

import android.app.Service
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vastausf.tasks.R
import com.vastausf.tasks.di.fragment.DaggerFragmentComponent
import com.vastausf.tasks.model.api.tasksApiData.TaskDataFull
import com.vastausf.tasks.presentation.adapter.TasksRecyclerView
import com.vastausf.tasks.presentation.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_task_list.view.*
import javax.inject.Inject

class TaskListFragment: BaseFragment(), TaskListFragmentView, TasksRecyclerView.TaskListener {

    @Inject
    @get:ProvidePresenter
    @field:InjectPresenter
    lateinit var presenter: TaskListFragmentPresenter

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

    override fun updateSearchState() {
        val state = presenter.searchState

        view?.apply {
            etTaskTitleSearch.isEnabled = state

            if (state) {
                bTaskSearch.visibility = View.GONE

                etTaskTitleSearch.requestFocus()

                (tasksApplication
                    .getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .showSoftInput(etTaskTitleSearch, InputMethodManager.SHOW_IMPLICIT)
            } else {
                bTaskSearch.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)

        view.apply {
            rvTasks.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = TasksRecyclerView(presenter.taskList).apply {
                    listener = this@TaskListFragment
                }
            }

            srlTasks.setOnRefreshListener {
                presenter.loadTaskList()
            }

            bTaskSearch.setOnClickListener {
                presenter.searchState = true
            }

            etTaskTitleSearch.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    presenter.taskDataSearch.title = s.toString()
                    presenter.loadTaskList()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            etTaskTitleSearch.setOnKeyListener { _, _, event ->
                if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
                    presenter.searchState = false
                }

                return@setOnKeyListener true
            }
        }

        activity?.setActionBar(view.findViewById(R.id.tbTaskList))

        return view
    }

    override fun onTaskClick(taskData: TaskDataFull) {
        showToast(taskData)
    }

    override fun updateLoadState() {
        val state = presenter.loadState

        view?.apply {
            srlTasks.isRefreshing = state
        }
    }

    override fun bindTaskList() {
        view?.apply {
            rvTasks.adapter?.notifyDataSetChanged()
        }
    }

    override fun onStart() {
        super.onStart()

        presenter.loadTaskList()
    }

}