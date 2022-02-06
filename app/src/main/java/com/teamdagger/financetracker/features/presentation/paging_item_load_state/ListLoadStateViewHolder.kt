package com.teamdagger.financetracker.features.presentation.paging_item_load_state

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.teamdagger.financetracker.R
import com.teamdagger.financetracker.databinding.LogsListLoadStateFooterItemBinding

class ListLoadStateViewHolder(private val binding: LogsListLoadStateFooterItemBinding,
                              retry: () -> Unit):RecyclerView.ViewHolder(binding.root) {
    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): ListLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.logs_list_load_state_footer_item, parent, false)
            val binding = LogsListLoadStateFooterItemBinding.bind(view)
            return ListLoadStateViewHolder(binding, retry)
        }
    }
}