package com.vastausf.tasks.presentation.activity.base

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v4.app.FragmentTransaction
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.vastausf.tasks.R
import com.vastausf.tasks.TasksApplication
import com.vastausf.tasks.presentation.fragment.base.BaseFragment

@SuppressLint("Registered")
abstract class BaseActivity : MvpAppCompatActivity(), BaseActivityView {

    val tasksApplication by lazy {
        application as TasksApplication
    }

    override fun launchActivity(activity: BaseActivity, finish: Boolean) {
        Intent(this, activity::class.java).apply {
            startActivity(this@apply)
            if (finish)
                finishAffinity()
        }
    }

    override fun launchFragment(fragment: BaseFragment, finish: Boolean) {
        supportFragmentManager?.beginTransaction()
            ?.apply {
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                replace(R.id.fragmentContainer, fragment)
                if (!finish) addToBackStack(fragment.tag)
            }
            ?.commit()

    }

    override fun showToast(text: Any) {
        Toast.makeText(this, text.toString(), Toast.LENGTH_SHORT)
            .show()
    }

}
