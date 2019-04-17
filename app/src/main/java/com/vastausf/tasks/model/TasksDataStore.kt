package com.vastausf.tasks.model

import android.content.SharedPreferences
import javax.inject.Inject

class TasksDataStore
@Inject
constructor(
    private val tasksSharedPreferences: SharedPreferences
) {
    private val loginSharedPreferences = "login"
    private var loginCache: String? = null
    var login: String
        get() {
            return loginCache ?: tasksSharedPreferences
                .getString(loginSharedPreferences, null) ?: ""
        }
        set(value) {
            loginCache = value

            tasksSharedPreferences
                .edit()
                .putString(loginSharedPreferences, value)
                .apply()
        }

}
