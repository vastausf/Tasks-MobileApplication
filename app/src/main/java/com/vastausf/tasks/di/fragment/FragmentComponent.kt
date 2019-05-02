package com.vastausf.tasks.di.fragment

import com.vastausf.tasks.di.application.ApplicationComponent
import com.vastausf.tasks.presentation.fragment.login.LoginFragment
import com.vastausf.tasks.presentation.fragment.main.MainFragment
import com.vastausf.tasks.presentation.fragment.project.ProjectFragment
import com.vastausf.tasks.presentation.fragment.projectList.ProjectListFragment
import com.vastausf.tasks.presentation.fragment.taskList.TaskListFragment
import com.vastausf.tasks.presentation.fragment.userList.UserListFragment
import dagger.Component

@Component(dependencies = [ApplicationComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {

    fun inject(loginFragment: LoginFragment)

    fun inject(mainFragment: MainFragment)

    fun inject(projectFragment: ProjectFragment)

    fun inject(taskListFragment: TaskListFragment)

    fun inject(projectListFragment: ProjectListFragment)

    fun inject(userListFragment: UserListFragment)

}