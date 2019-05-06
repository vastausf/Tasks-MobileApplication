package com.vastausf.tasks.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vastausf.tasks.R
import kotlinx.android.synthetic.main.item_document.view.*

class DocumentsRecyclerView(
    val listener: DocumentsListener,
    private val itemList: List<String>
) : RecyclerView.Adapter<DocumentsRecyclerView.ViewHolder>() {

    interface DocumentsListener {
        fun onDocumentClick(document: String, view: View)

        fun onDocumentLongClick(document: String, view: View) {

        }
    }

    override fun onCreateViewHolder(container: ViewGroup, layoutType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(container.context).inflate(R.layout.item_document, container, false)
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(itemList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: String) {
            itemView.tvDocumentTitle.text = item

            itemView.setOnClickListener {
                listener.onDocumentClick(item, itemView)
            }

            itemView.setOnLongClickListener {
                listener.onDocumentLongClick(item, itemView)

                return@setOnLongClickListener true
            }
        }

    }

}