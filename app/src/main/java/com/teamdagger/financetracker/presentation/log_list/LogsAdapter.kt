package com.teamdagger.financetracker.presentation.log_list

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.synapsisid.smartdeskandroombooking.util.Util
import com.teamdagger.financetracker.R
import com.teamdagger.financetracker.databinding.LayoutLogsBinding
import com.teamdagger.financetracker.domain.entities.Logs

class LogsAdapter(private val context: Context): PagingDataAdapter<Logs, LogsAdapter.CustomVH>(LogsComparator)  {

    private var onLongClickListener : ((Logs, Int)->Unit)? = null

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
        if(!getItem(position)!!.isVisible){
            holder.cardLog.visibility = View.GONE
        }

        holder.tvCategory.text = getItem(position)?.category
        holder.tvDesc.text = getItem(position)?.desc
        holder.tvDate.text = getItem(position)?.date
        holder.tvNominal.text = "Rp.${getItem(position)!!.nominal}"
        holder.ivCategory.setImageDrawable(getIcon(getItem(position)!!.category))

        holder.cardLog.setOnLongClickListener {
            onLongClickListener?.invoke(getItem(holder.absoluteAdapterPosition)!!, holder.absoluteAdapterPosition)
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

    fun onLongClick(listener:(Logs, Int)-> Unit){
        this.onLongClickListener = listener
    }

    object LogsComparator : DiffUtil.ItemCallback<Logs>() {
        override fun areItemsTheSame(oldItem: Logs, newItem: Logs): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Logs, newItem: Logs): Boolean {
            return oldItem == newItem
        }
    }
}