package com.teamdagger.financetracker.presentation.logs_detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamdagger.financetracker.databinding.LayoutLogsDetailBinding
import com.teamdagger.financetracker.domain.entities.LogsDetail

class LogsDetailAdapter(private val context: Context):RecyclerView.Adapter<LogsDetailAdapter.CustomVH>() {

    private val listData = mutableListOf<LogsDetail>()

    class CustomVH(view: LayoutLogsDetailBinding):RecyclerView.ViewHolder(view.root) {

        val tvCategory = view.tvLogsCategory
        val tvTotal = view.tvLogsTotal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomVH {
        return CustomVH(LayoutLogsDetailBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: CustomVH, position: Int) {

        holder.tvCategory.text = listData[position].category
        holder.tvTotal.text = "Rp.${listData[position].total}"


    }


    fun setNewData(listData: List<LogsDetail>){
        this.listData.clear()
        this.listData.addAll(listData)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = listData.size
}