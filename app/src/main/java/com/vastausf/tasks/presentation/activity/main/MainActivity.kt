package com.vastausf.tasks.presentation.activity.main

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vastausf.tasks.R
import com.vastausf.tasks.di.activity.DaggerActivityComponent
import com.vastausf.tasks.presentation.activity.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity(), MainActivityView {

    @Inject
    @get:ProvidePresenter
    @field:InjectPresenter
    lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            DaggerActivityComponent
                .builder()
                .applicationComponent(tasksApplication.component)
                .build()
                .inject(this)
        }
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        presenter.onCreate()
    }

}
