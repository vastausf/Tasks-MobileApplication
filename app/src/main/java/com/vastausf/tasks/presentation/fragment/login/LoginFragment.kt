package com.vastausf.tasks.presentation.fragment.login

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vastausf.tasks.R
import com.vastausf.tasks.di.fragment.DaggerFragmentComponent
import com.vastausf.tasks.presentation.fragment.base.BaseFragment
import com.vastausf.tasks.utils.getHashSHA256
import com.vastausf.tasks.utils.trimAllSpaces
import kotlinx.android.synthetic.main.fragment_login.view.*
import javax.inject.Inject

class LoginFragment : BaseFragment(), LoginFragmentView {

    @Inject
    @get:ProvidePresenter
    @field:InjectPresenter
    lateinit var presenter: LoginFragmentPresenter

    override fun bindData(login: String) {
        view?.etLogin?.setText(login)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        view.etPassword.apply {
            val tf = typeface
            inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
            typeface = tf
        }

        view.bSignIn.setOnClickListener {
            presenter.onLogin(view.etLogin.text.toString().trimAllSpaces(), view.etPassword.text.toString().getHashSHA256())
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

        presenter.onCreate()
    }

}
