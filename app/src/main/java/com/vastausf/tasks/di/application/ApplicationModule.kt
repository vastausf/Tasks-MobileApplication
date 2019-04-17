package com.vastausf.tasks.di.application

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vastausf.tasks.R
import com.vastausf.tasks.TasksApplication
import com.vastausf.tasks.model.api.TasksApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class ApplicationModule(
    private val tasksApplication: TasksApplication
) {

    @Provides
    fun provideTasksApplication(): TasksApplication {
        return tasksApplication
    }

    @Provides
    fun provideTasksSharedPreferences(): SharedPreferences {
        return tasksApplication.getSharedPreferences("tasksApplication", Context.MODE_PRIVATE)
    }

    @Provides
    fun provideTasksApiService(
        tasksApplication: TasksApplication,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): TasksApiService {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(tasksApplication.getString(R.string.tasks_base_server_url))
            .build()
            .create(TasksApiService::class.java)
    }


    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .build()
    }


    @Provides
    fun provideMoshi(): Moshi {
        return Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

}
