package com.vastausf.tasks.presentation.fragment.taskEdit

import android.annotation.SuppressLint
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
import com.vastausf.tasks.model.api.tasksApiData.UserData
import com.vastausf.tasks.presentation.fragment.base.BaseFragment
import com.vastausf.tasks.presentation.fragment.userList.UserListFragment
import kotlinx.android.synthetic.main.fragment_task_edit.view.*
import javax.inject.Inject

class TaskEditFragment : BaseFragment(), TaskEditFragmentView {

    @Inject
    @get:ProvidePresenter
    @field:InjectPresenter
    lateinit var presenter: TaskEditFragmentPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.getInt("taskId")?.let { presenter.taskId = it }
        val view = inflater.inflate(R.layout.fragment_task_edit, container, false)

        view.apply {
            srlTaskEdit.setOnRefreshListener {
                presenter.loadTaskData()
            }

            etTaskEditTitle.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    presenter.taskDataEdit.title = s.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })

            etTaskEditDescription.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    presenter.taskDataEdit.description = s.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })

            bTaskEditAssignTo.setOnClickListener {
                launchFragment(UserListFragment().apply {
                    userListener = object : UserListFragment.UserListListener {
                        @SuppressLint("SetTextI18n")
                        override fun onUserClick(userData: UserData, fragment: UserListFragment) {
                            this@TaskEditFragment.apply {
                                presenter.taskDataEdit.assigned = userData.id

                                bTaskEditAssignTo.text = userData.let {
                                    "${it.firstName} ${it.lastName}"
                                }
                            }

                            fragment.goBack()
                        }
                    }
                })
            }

            bTaskEditApply.setOnClickListener {
                presenter.editTaskData()
            }
        }

        return view
    }

    override fun bindTaskData() {
        view?.apply {
            etTaskEditTitle.setText(presenter.taskData.title)
            etTaskEditDescription.setText(presenter.taskData.description)
            bTaskEditAssignTo.text = presenter.taskData.assignId.let {
                "${it.firstName} ${it.lastName}"
            }
        }
    }

    override fun updateLoadState() {
        val state = presenter.loadState

        view?.apply {
            srlTaskEdit.isRefreshing = state
        }
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

        presenter.loadTaskData()
    }

}
