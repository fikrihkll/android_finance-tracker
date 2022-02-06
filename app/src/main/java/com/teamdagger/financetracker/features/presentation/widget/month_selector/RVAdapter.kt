package com.teamdagger.financetracker.features.presentation.widget.month_selector

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class RichDemoRVAdapter(private val mModels: List<String>, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mInflater: LayoutInflater? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        mInflater = LayoutInflater.from(recyclerView.context)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        mInflater = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CustomVH(mInflater!!, parent, context)
    }

    override fun getItemCount() = mModels.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = mModels[position]
        (holder as ICustomVH).bind(model)
    }
}