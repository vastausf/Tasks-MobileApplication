package com.vastausf.tasks.presentation.fragment.taskList

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vastausf.tasks.R
import com.vastausf.tasks.di.fragment.DaggerFragmentComponent
import com.vastausf.tasks.model.api.tasksApiData.TaskDataFull
import com.vastausf.tasks.model.api.tasksApiData.UserData
import com.vastausf.tasks.presentation.adapter.TasksRecyclerView
import com.vastausf.tasks.presentation.adapter.UsersRecyclerView
import com.vastausf.tasks.presentation.fragment.base.BaseFragment
import com.vastausf.tasks.presentation.fragment.userList.UserListFragment
import kotlinx.android.synthetic.main.bottom_sheet_task_search.view.*
import kotlinx.android.synthetic.main.fragment_task_list.view.*
import javax.inject.Inject

class TaskListFragment : BaseFragment(), TaskListFragmentView, TasksRecyclerView.TaskListener, UsersRecyclerView.UserListener {

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

    override fun onUserClick(userData: UserData) {
        showToast(userData)
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

            srlTaskList.setOnRefreshListener {
                presenter.loadTaskList()
            }

            bSearchTask.setOnClickListener {
                BottomSheetBehavior.from(llBottomSearchView).state = BottomSheetBehavior.STATE_EXPANDED
            }

            llBottomSearchView.apply {
                bClearSearch.setOnClickListener {
                    etTaskTitleSearch.setText("")
                    etTaskDescriptionSearch.setText("")

                    bTaskSearchCreatedBy.setText(R.string.created_by)
                    presenter.taskDataSearch.creatorId = null
                    bTaskSearchAssignedTo.setText(R.string.assign_to)
                    presenter.taskDataSearch.assignId = null

                    BottomSheetBehavior.from(llBottomSearchView).state = BottomSheetBehavior.STATE_HIDDEN
                }

                etTaskTitleSearch.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        presenter.taskDataSearch.title = s.toString()

                        presenter.loadTaskList()
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    }
                })

                etTaskDescriptionSearch.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        presenter.taskDataSearch.description = s.toString()

                        presenter.loadTaskList()
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    }
                })

                bTaskSearchCreatedBy.setOnClickListener {
                    UserListFragment().apply {
                        userListener = object : UserListFragment.UserListListener {
                            @SuppressLint("SetTextI18n")
                            override fun onUserClick(userData: UserData, fragment: UserListFragment) {
                                this@TaskListFragment.presenter.taskDataSearch.creatorId = listOf(userData.id)
                                bTaskSearchCreatedBy.text = "${getString(R.string.created_by)}: ${userData.firstName} ${userData.lastName}"
                                fragment.goBack()

                                this@TaskListFragment.presenter.loadTaskList()
                            }
                        }
                        this@TaskListFragment.launchFragment(this)
                    }
                }

                bTaskSearchAssignedTo.setOnClickListener {
                    UserListFragment().apply {
                        userListener = object : UserListFragment.UserListListener {
                            @SuppressLint("SetTextI18n")
                            override fun onUserClick(userData: UserData, fragment: UserListFragment) {
                                this@TaskListFragment.presenter.taskDataSearch.assignId = listOf(userData.id)
                                bTaskSearchAssignedTo.text = "${getString(R.string.assign_to)}: ${userData.firstName} ${userData.lastName}"
                                fragment.goBack()

                                this@TaskListFragment.presenter.loadTaskList()
                            }
                        }
                        this@TaskListFragment.launchFragment(this)
                    }
                }
            }

            BottomSheetBehavior.from(llBottomSearchView).state = BottomSheetBehavior.STATE_HIDDEN
        }

        return view
    }

    override fun onTaskClick(taskData: TaskDataFull) {
        showToast(taskData)
    }

    override fun updateLoadState() {
        val state = presenter.loadState

        view?.apply {
            srlTaskList.isRefreshing = state
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