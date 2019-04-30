package com.vastausf.tasks.presentation.fragment.base

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.vastausf.tasks.R
import com.vastausf.tasks.TasksApplication
import com.vastausf.tasks.presentation.activity.base.BaseActivity

@SuppressLint("Registered")
abstract class BaseFragment : MvpAppCompatFragment(), BaseFragmentView {

    open val tasksApplication: TasksApplication by lazy {
        activity?.application as TasksApplication
    }

    override fun launchFragment(fragment: BaseFragment, finish: Boolean, container: Int, bundle: Bundle?) {
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            hide(this@BaseFragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            fragment.arguments = bundle
            add(R.id.fragmentContainer, fragment)
            if (!finish) addToBackStack(fragment.tag)
        }?.commit()
    }

    override fun replaceFragment(fragment: BaseFragment, finish: Boolean, container: Int) {
        fragmentManager?.beginTransaction()?.apply {
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            replace(container, fragment)
        }?.commit()
    }

    override fun showToast(text: Any) {
        Toast.makeText(activity, text.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun showToast(text: Int) {
        Toast.makeText(activity, getString(text), Toast.LENGTH_SHORT).show()
    }

    override fun launchActivity(activity: BaseActivity, finish: Boolean) {
        Intent(this.activity, activity::class.java).apply {
            this@BaseFragment.activity?.startActivity(this)
            if (finish) this@BaseFragment.activity?.finishAffinity()
        }
    }

    override fun goBack() {
        fragmentManager?.popBackStack()
    }

}
