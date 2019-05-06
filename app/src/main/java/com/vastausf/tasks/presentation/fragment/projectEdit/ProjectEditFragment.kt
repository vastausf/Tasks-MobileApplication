package com.vastausf.tasks.presentation.fragment.projectEdit

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import com.vastausf.tasks.model.api.tasksApiData.UserData
import com.vastausf.tasks.presentation.adapter.DocumentsRecyclerView
import com.vastausf.tasks.presentation.adapter.UsersRecyclerView
import com.vastausf.tasks.presentation.fragment.base.BaseFragment
import com.vastausf.tasks.presentation.fragment.userList.UserListFragment
import kotlinx.android.synthetic.main.fragment_project_edit.view.*
import javax.inject.Inject

class ProjectEditFragment : BaseFragment(), ProjectEditFragmentView, DocumentsRecyclerView.DocumentsListener, UsersRecyclerView.UserListener {

    @Inject
    @get:ProvidePresenter
    @field:InjectPresenter
    lateinit var presenter: ProjectEditFragmentPresenter

    override fun onDocumentClick(document: String, view: View) {
        Intent(Intent.ACTION_VIEW).apply {
            if (document.startsWith("http") || document.startsWith("https")) {
                data = Uri.parse(document)

                startActivity(this)
            } else {
                showToast(R.string.error)
            }
        }
    }

    override fun onDocumentLongClick(document: String, view: View) {
        AlertDialog
            .Builder(view.context)
            .setTitle("${getString(R.string.delete)} '$document'?")
            .setPositiveButton(R.string.delete) { dialog, which ->
                presenter.documentList.remove(document)

                this@ProjectEditFragment.view?.rvProjectEditDocuments?.adapter?.notifyDataSetChanged()
            }
            .create()
            .show()
    }

    override fun onUserClick(userData: UserData, view: View) {
        showToast(userData)
    }

    override fun onUserLongClick(userData: UserData, view: View) {
        AlertDialog
            .Builder(view.context)
            .setTitle("${getString(R.string.delete)} '${userData.firstName} ${userData.lastName}'?")
            .setPositiveButton(R.string.delete) { dialog, which ->
                presenter.userList.remove(userData)

                this@ProjectEditFragment.view?.rvProjectEditUsers?.adapter?.notifyDataSetChanged()
            }
            .create()
            .show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.getInt("projectId")?.let {
            presenter.projectId = it
        }

        val view = inflater.inflate(R.layout.fragment_project_edit,container,false)

        view.apply {
            etProjectEditTitle.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    presenter.projectDataEdit.title = s.toString()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })

            etProjectEditSpecification.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    presenter.projectDataEdit.specification = s.toString()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })

            etProjectEditDescription.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    presenter.projectDataEdit.description = s.toString()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })

            srlProjectEdit.setOnRefreshListener {
                presenter.loadProjectData()
            }

            bProjectEditApply.setOnClickListener {
                presenter.editProjectData()
            }

            bProjectEditDocuments.setOnClickListener {
                llProjectEditDocuments.visibility = View.VISIBLE
                llProjectEditUser.visibility = View.GONE
            }

            bProjectEditUsers.setOnClickListener {
                llProjectEditDocuments.visibility = View.GONE
                llProjectEditUser.visibility = View.VISIBLE
            }

            rvProjectEditDocuments.apply {
                adapter = DocumentsRecyclerView(this@ProjectEditFragment, presenter.documentList)
                layoutManager = LinearLayoutManager(context)
            }

            rvProjectEditUsers.apply {
                adapter = UsersRecyclerView(this@ProjectEditFragment, presenter.userList)
                layoutManager = LinearLayoutManager(context)
            }

            bAddDocument.setOnClickListener {
                val newDocument = etProjectEditNewDocument.text.toString()

                if (newDocument.isNotEmpty() && !presenter.documentList.contains(newDocument)) {
                    presenter.documentList.add(newDocument)

                    this@ProjectEditFragment.view?.rvProjectEditDocuments?.adapter?.notifyDataSetChanged()
                }
            }

            bAddUser.setOnClickListener {
                launchFragment(UserListFragment().apply {
                    userListener = object : UserListFragment.UserListListener {
                        override fun onUserClick(userData: UserData, fragment: UserListFragment) {
                            if (!this@ProjectEditFragment.presenter.userList.contains(userData)) {
                                this@ProjectEditFragment.presenter.userList.add(userData)

                                fragment.goBack()
                                this@ProjectEditFragment.view?.rvProjectEditUsers?.adapter?.notifyDataSetChanged()
                            } else {
                                showToast(R.string.already_added)
                            }

                        }
                    }
                })
            }
        }

        return view
    }

    override fun updateLoadStatus() {
        val state = presenter.loadStatus
        view?.apply {
            srlProjectEdit.isRefreshing = state
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

    override fun bindProjectData() {
        view?.apply {
            etProjectEditTitle.setText(presenter.projectData.title)
            etProjectEditSpecification.setText(presenter.projectData.specification)
            etProjectEditDescription.setText(presenter.projectData.description)

            rvProjectEditDocuments?.adapter?.notifyDataSetChanged()
            rvProjectEditUsers?.adapter?.notifyDataSetChanged()
        }
    }

    override fun onStart() {
        super.onStart()

        presenter.loadProjectData()
    }

}
