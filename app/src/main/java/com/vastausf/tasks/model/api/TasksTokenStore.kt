package com.vastausf.tasks.model.api

import android.content.SharedPreferences
import javax.inject.Inject

class TasksTokenStore
@Inject
constructor(
    private val tasksSharedPreferences: SharedPreferences
) {

    var userId: Int = 0
        get() {
            field = accessToken.split(":")[0].toInt()

            return field
        }

    private val accessTokenSharedPreferences = "accessToken"
    private var accessTokenCache: String? = null
    var accessToken: String
        get() {
            return accessTokenCache ?: tasksSharedPreferences
                .getString(accessTokenSharedPreferences, null) ?: ""
        }
        set(value) {
            accessTokenCache = value

            tasksSharedPreferences
                .edit()
                .putString(accessTokenSharedPreferences, value)
                .apply()
        }

    fun clearTokens() {
        tasksSharedPreferences.edit().apply {
            remove(accessTokenSharedPreferences)
        }.apply()
    }
}
