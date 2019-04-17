package com.vastausf.tasks.presentation.activity

import android.os.Bundle
import com.vastausf.tasks.presentation.activity.base.BaseActivity
import com.vastausf.tasks.presentation.activity.main.MainActivity

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        launchActivity(MainActivity(), finish = true)
    }
}