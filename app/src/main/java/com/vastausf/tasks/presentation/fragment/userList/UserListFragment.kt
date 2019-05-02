package com.vastausf.tasks.presentation.fragment.userList

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
import com.vastausf.tasks.model.api.tasksApiData.UserData
import com.vastausf.tasks.presentation.adapter.UsersRecyclerView
import com.vastausf.tasks.presentation.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.bottom_sheet_user_search.view.*
import kotlinx.android.synthetic.main.fragment_user_list.view.*
import javax.inject.Inject

class UserListFragment: BaseFragment(), UserListFragmentView, UsersRecyclerView.UserListener {

    @Inject
    @get:ProvidePresenter
    @field:InjectPresenter
    lateinit var presenter: UserListFragmentPresenter

    var userListener: UserListListener? = null

    interface UserListListener {

        fun onUserClick(userData: UserData, fragment: UserListFragment)

    }

    override fun updateLoadState() {
        view?.apply {
            srlUserList.isRefreshing = presenter.loadState
        }
    }

    override fun onUserClick(userData: UserData) {
        userListener?.onUserClick(userData, this)
    }

    override fun updateUserList() {
        view?.rvUserList?.adapter?.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_list, container, false)

        view.apply {
            rvUserList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = UsersRecyclerView(presenter.userList).apply {
                    listener = this@UserListFragment
                }
            }

            srlUserList.setOnRefreshListener {
                presenter.loadUserList()
            }

            bSearchUser.setOnClickListener {
                BottomSheetBehavior.from(llBottomSearchView).state = BottomSheetBehavior.STATE_EXPANDED
            }

            llBottomSearchView.apply {
                bClearSearch.setOnClickListener {
                    etSearchFirstName.setText("")
                    etSearchLastName.setText("")
                    etSearchMiddleName.setText("")
                    etSearchEmail.setText("")

                    BottomSheetBehavior.from(llBottomSearchView).state = BottomSheetBehavior.STATE_HIDDEN
                }

                etSearchFirstName.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        presenter.userDataSearch.firstName = s.toString()

                        presenter.loadUserList()
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    }
                })

                etSearchLastName.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        presenter.userDataSearch.lastName = s.toString()

                        presenter.loadUserList()
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    }
                })

                etSearchMiddleName.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        presenter.userDataSearch.middleName = s.toString()

                        presenter.loadUserList()
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    }
                })

                etSearchEmail.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        presenter.userDataSearch.email = s.toString()

                        presenter.loadUserList()
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

    override fun onStart() {
        super.onStart()

        presenter.loadUserList()
    }

}