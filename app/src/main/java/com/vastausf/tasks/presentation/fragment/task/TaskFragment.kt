@file:SuppressLint("SetTextI18n")
package com.vastausf.tasks.presentation.fragment.task

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vastausf.tasks.R
import com.vastausf.tasks.di.fragment.DaggerFragmentComponent
import com.vastausf.tasks.model.api.tasksApiData.TaskDataEdit
import com.vastausf.tasks.model.api.tasksApiData.TaskStatus
import com.vastausf.tasks.model.api.tasksApiData.UserData
import com.vastausf.tasks.presentation.fragment.base.BaseFragment
import com.vastausf.tasks.presentation.fragment.projectInfo.ProjectInfoFragment
import com.vastausf.tasks.presentation.fragment.userList.UserListFragment
import kotlinx.android.synthetic.main.fragment_task.view.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TaskFragment: BaseFragment(), TaskFragmentView {

    @Inject
    @get:ProvidePresenter
    @field:InjectPresenter
    lateinit var presenter: TaskFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter.taskId = arguments?.getInt("taskId") ?: 0

        val view = inflater.inflate(R.layout.fragment_task, container, false)

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

    override fun bindTaskData() {
        view?.apply {
            etTitle.apply {
                setText(presenter.taskData.title)

                addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        presenter.taskDataEdit.title = s.toString()
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    }

                })
            }

            etDescription.apply {
                setText(presenter.taskData.description)

                addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        presenter.taskDataEdit.description = s.toString()
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    }

                })
            }

            tvCreatedBy.text = "${presenter.taskData.creatorId.firstName} ${presenter.taskData.creatorId.lastName}"

            tvAssignTo.apply {
                text = "${presenter.taskData.assignId.firstName} ${presenter.taskData.assignId.lastName}"

                setOnClickListener {
                    launchFragment(UserListFragment().apply {
                        userListener = object: UserListFragment.UserListListener {
                            override fun onUserClick(userData: UserData, fragment: UserListFragment) {
                                tvAssignTo.text = "${userData.firstName} ${userData.lastName}"
                                this@TaskFragment.presenter.taskDataEdit.assigned = userData.id

                                fragment.goBack()
                            }
                        }
                    })
                }
            }

            bTaskStatus.apply {
                text = getString(TaskStatus.fromInt(presenter.taskData.status))

                setOnClickListener {
                    val items = presenter.taskData.history
                    AlertDialog
                        .Builder(context)
                        .setItems(items.map {
                            "${it.statusTo}: ${it.comment}"
                        }.toTypedArray()) { dialog, which ->
                            val item = items[which]

                            AlertDialog
                                .Builder(context)
                                .setMessage(item.comment)
                                .setTitle(SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault()).format(Date(item.time)))
                                .create()
                                .show()
                        }
                        .create()
                        .show()
                }
            }

            fabEditTask.apply {
                setOnClickListener {
                    presenter.editModeState = !presenter.editModeState
                }
            }

            srlTask.setOnRefreshListener {
                presenter.loadTaskData()
            }

            bProject.setOnClickListener {
                launchFragment(ProjectInfoFragment(), bundle = Bundle().apply {
                    putInt("projectId", presenter.taskData.projectId)
                })
            }
        }
    }

    override fun updateLoadState() {
        view?.apply {
            srlTask.isRefreshing = presenter.loadState
        }
    }

    override fun updateEditState() {
        view?.apply {
            val state = presenter.editModeState

            etTitle.isEnabled = state
            etDescription.isEnabled = state

            tvAssignTo.isEnabled = state
        }
    }

    override fun updateCanEdit() {
        view?.apply {
            @SuppressLint("RestrictedApi")
            fabEditTask.visibility = if (presenter.canEdit) View.VISIBLE else View.GONE
        }
    }

    override fun onStart() {
        super.onStart()

        presenter.loadTaskData()
    }

}