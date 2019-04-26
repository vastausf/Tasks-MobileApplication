package com.vastausf.tasks.presentation.adapter.pages

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vastausf.tasks.R
import com.vastausf.tasks.model.api.tasksApiData.TaskDataFull
import com.vastausf.tasks.presentation.adapter.TasksAdapter
import kotlinx.android.synthetic.main.fragment_project_tasks.*

class FragmentTasks : Fragment() {

    private val taskList = mutableListOf<TaskDataFull>()

    lateinit var tasksAdapter: TasksAdapter

    fun bindTasks(tasks: List<TaskDataFull>) {
        taskList.clear()
        taskList.addAll(tasks)
        tasksAdapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tasksAdapter = TasksAdapter(taskList)
        tasksAdapter.listener = parentFragment as TasksAdapter.TaskListener

        rvTasks.adapter = tasksAdapter
        rvTasks.layoutManager = LinearLayoutManager(this.context)
    }

}
