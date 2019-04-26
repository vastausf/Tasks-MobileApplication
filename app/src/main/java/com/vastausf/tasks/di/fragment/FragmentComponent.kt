package com.vastausf.tasks.di.fragment

import com.vastausf.tasks.di.application.ApplicationComponent
import com.vastausf.tasks.presentation.fragment.login.LoginFragment
import com.vastausf.tasks.presentation.fragment.main.MainFragment
import com.vastausf.tasks.presentation.fragment.newProject.NewProjectFragment
import com.vastausf.tasks.presentation.fragment.project.ProjectFragment
import dagger.Component

@Component(dependencies = [ApplicationComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {

    fun inject(loginFragment: LoginFragment)

    fun inject(mainFragment: MainFragment)

    fun inject(projectFragment: ProjectFragment)

    fun inject(newProjectFragment: NewProjectFragment)

}