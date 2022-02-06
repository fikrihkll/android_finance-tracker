package com.teamdagger.financetracker.features.presentation.widget.month_selector

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teamdagger.financetracker.R
import com.teamdagger.financetracker.core.pager_snap_helper.RCPageScrollState
import com.teamdagger.financetracker.core.pager_snap_helper.RVPagerSnapHelperListenable
import com.teamdagger.financetracker.core.pager_snap_helper.RVPagerStateListener
import com.teamdagger.financetracker.core.pager_snap_helper.VisiblePageState
import com.teamdagger.financetracker.databinding.FragmentMonthSelectorBinding

class MonthSelectorFragment : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private lateinit var binding: FragmentMonthSelectorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMonthSelectorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcMonth.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        binding.rcMonth.adapter = RichDemoRVAdapter(listOf("January", "February", "March", "May"), view.context)

        RVPagerSnapHelperListenable().attachToRecyclerView(binding.rcMonth, object : RVPagerStateListener {
            override fun onPageScroll(pagesState: List<VisiblePageState>) {
                for (pageState in pagesState) {
                    val vh = binding.rcMonth.findContainingViewHolder(pageState.view) as ICustomVH
                    vh.setRealtimeAttr(pageState.index, pageState.viewCenterX.toString(), pageState.distanceToSettled, pageState.distanceToSettledPixels)
                }
            }

            override fun onScrollStateChanged(state: RCPageScrollState) {
            }

            override fun onPageSelected(index: Int) {
                val vh = binding.rcMonth.findViewHolderForAdapterPosition(index) as ICustomVH?
                vh?.onSelected()
            }
        })

    }
}