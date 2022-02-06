package com.teamdagger.financetracker.features.presentation.widget.month_selector

import androidx.annotation.Px

interface ICustomVH {
    fun bind(model: String)
    fun setRealtimeAttr(index: Int, centerXText: String, horizOffsetRate: Float, @Px horizOffsetPx: Int)
    fun onSelected()
}