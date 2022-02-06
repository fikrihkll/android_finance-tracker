package com.teamdagger.financetracker.features.presentation.widget.month_selector

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView
import com.teamdagger.financetracker.R
import com.teamdagger.financetracker.databinding.LayoutMonthSelectorBinding

class CustomVH : RecyclerView.ViewHolder, ICustomVH {

    private lateinit var context: Context

    private val mPageIndexText: TextView

    private val mPositioningText: TextView

    constructor(inflater: LayoutInflater, parent: ViewGroup,context: Context)
            : this(LayoutMonthSelectorBinding.inflate(inflater, parent, false) as ViewGroup, context)

    constructor(listPageView: ViewGroup, context: Context)
            : super(listPageView) {

        this.context = context
        mPageIndexText = listPageView.findViewById(R.id.pageIndex)
        mPositioningText  = listPageView.findViewById(R.id.positioningText)

    }

    override fun bind(model: String) {

        var color: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getColor(R.color.blue_bright)
        } else {
            context.resources.getColor(R.color.blue_bright)
        }

        itemView.setBackgroundColor(color)

    }

    override fun setRealtimeAttr(index: Int, centerXText: String, horizOffsetRate: Float, @Px horizOffsetPx: Int) {
        mPageIndexText.text = "Page #${index}"
        mPositioningText.text = "${centerXText}px (${horizOffsetPx}px)"

    }

    override fun onSelected() {
        val scaleXAnim = ObjectAnimator.ofFloat(mPageIndexText, View.SCALE_X, 0.75f, 1.8f, 1f, 0.8f, 1f)
        val scaleYAnim = ObjectAnimator.ofFloat(mPageIndexText, View.SCALE_Y, 0.75f, 1.8f, 1f, 0.8f, 1f)


        val animation = AnimatorSet()
        animation.playTogether(scaleXAnim, scaleYAnim)
        animation.duration = 600

        animation.start()
    }
}