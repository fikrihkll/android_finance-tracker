package com.teamdagger.financetracker.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamdagger.financetracker.databinding.LayoutLogsBinding
import com.teamdagger.financetracker.domain.entities.Logs

class RecentLogsAdapter(private val context: Context):RecyclerView.Adapter<RecentLogsAdapter.CustomVH>() {

    private val listData = mutableListOf<Logs>()

    class CustomVH(view: LayoutLogsBinding):RecyclerView.ViewHolder(view.root) {

        val tvCategory = view.tvCategory
        val ivCategory = view.ivCategory
        val tvDesc = view.tvDesc
        val tvDate = view.tvDate
        val tvNominal = view.tvNominal

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomVH {
        return CustomVH(LayoutLogsBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: CustomVH, position: Int) {

        holder.tvCategory.text = listData[position].category
        holder.tvDesc.text = listData[position].desc
        holder.tvDate.text = listData[position].date
        holder.tvNominal.text = "Rp.${listData[position].nominal}"

    }

    fun setNewData(listData: List<Logs>){
        this.listData.clear()
        this.listData.addAll(listData)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = listData.size
}