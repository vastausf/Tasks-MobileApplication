package com.vastausf.tasks.presentation.adapter.pages

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vastausf.tasks.R
import kotlinx.android.synthetic.main.fragment_project_main.view.*

class FragmentProjectMain: Fragment() {

    interface ProjectListener {
        fun onReload()

        fun onDocumentsClick()

        fun onSpecificationClick()
    }

    lateinit var listener: ProjectListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_project_main, container, false)

        view.srlProject.setOnRefreshListener {
            listener.onReload()
        }

        view.bDocuments.setOnClickListener {
            listener.onDocumentsClick()
        }

        view.bSpecification.setOnClickListener {
            listener.onSpecificationClick()
        }

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        listener = parentFragment as ProjectListener
    }

}