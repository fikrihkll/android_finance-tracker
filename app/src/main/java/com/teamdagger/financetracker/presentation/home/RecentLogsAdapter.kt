package com.teamdagger.financetracker.presentation.home

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.synapsisid.smartdeskandroombooking.util.Util
import com.teamdagger.financetracker.R
import com.teamdagger.financetracker.databinding.LayoutLogsBinding
import com.teamdagger.financetracker.domain.entities.Logs

class RecentLogsAdapter(private val context: Context):RecyclerView.Adapter<RecentLogsAdapter.CustomVH>() {

    private val listData = mutableListOf<Logs>()

    private var onLongClickListener : ((Long)->Unit)? = null

    class CustomVH(view: LayoutLogsBinding):RecyclerView.ViewHolder(view.root) {

        val tvCategory = view.tvCategory
        val ivCategory = view.ivCategory
        val tvDesc = view.tvDesc
        val tvDate = view.tvDate
        val tvNominal = view.tvNominal
        val cardLog = view.cardLog
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomVH {
        return CustomVH(LayoutLogsBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: CustomVH, position: Int) {

        holder.tvCategory.text = listData[position].category
        holder.tvDesc.text = listData[position].desc
        holder.tvDate.text = listData[position].date
        holder.tvNominal.text = "Rp.${listData[position].nominal}"
        holder.ivCategory.setImageDrawable(getIcon(listData[position].category))

        holder.cardLog.setOnLongClickListener {
            onLongClickListener?.invoke(listData[position].id)
            true
        }
    }

    private fun getIcon(category: String):Drawable?{
        when(category){
            Util.listCategory[0] -> {
                return context.getDrawable(R.drawable.ic_baseline_set_meal_24)
            }
            Util.listCategory[1] -> {
                return context.getDrawable(R.drawable.ic_baseline_backpack_24)
            }
            Util.listCategory[2] -> {
                return context.getDrawable(R.drawable.ic_baseline_room_service_24)
            }
            Util.listCategory[3] -> {
                return context.getDrawable(R.drawable.ic_baseline_coffee_24)
            }
            Util.listCategory[4] -> {
                return context.getDrawable(R.drawable.ic_baseline_shopping_bag_24)
            }
            Util.listCategory[5] -> {
                return context.getDrawable(R.drawable.ic_baseline_subscriptions_24)
            }
            Util.listCategory[6] -> {
                return context.getDrawable(R.drawable.ic_baseline_directions_transit_24)
            }
            else -> {
                return context.getDrawable(R.drawable.ic_baseline_backpack_24)
            }
        }
    }

    fun onLongClick(listener:(Long)-> Unit){
        this.onLongClickListener = listener
    }

    fun setNewData(listData: List<Logs>){
        this.listData.clear()
        this.listData.addAll(listData)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = listData.size
}