package com.teamdagger.financetracker.presentation.paging_item_load_state

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class ListLoadStateAdapter(private val retry: () -> Unit): LoadStateAdapter<ListLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: ListLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ListLoadStateViewHolder {
        return ListLoadStateViewHolder.create(parent, retry)
    }
}