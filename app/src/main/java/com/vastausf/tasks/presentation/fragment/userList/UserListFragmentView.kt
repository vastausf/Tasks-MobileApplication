package com.vastausf.tasks.presentation.fragment.userList

import com.vastausf.tasks.presentation.fragment.base.BaseFragmentView

interface UserListFragmentView: BaseFragmentView {

    fun updateLoadState()

    fun updateUserList()

}
