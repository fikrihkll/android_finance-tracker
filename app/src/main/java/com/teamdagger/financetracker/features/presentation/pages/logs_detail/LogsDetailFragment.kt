package com.teamdagger.financetracker.features.presentation.pages.logs_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.synapsisid.smartdeskandroombooking.util.DataState
import com.teamdagger.financetracker.R
import com.teamdagger.financetracker.databinding.FragmentLogsDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogsDetailFragment(private val month: Int, private val year: Int) : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogStyle)
    }

    private lateinit var binding: FragmentLogsDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogsDetailBinding.inflate(inflater, container,false)
        return binding.root
    }

    val viewModel : LogsDetailViewModel by viewModels()

    private lateinit var adapter: LogsDetailAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRc()
        subscribeData()

        viewModel.setLogsDetailStateEvent(month, year)

    }

    private fun subscribeData(){
        viewModel.logsDetailStateEvent.observe(viewLifecycleOwner) {

            when (it) {
                is DataState.Success -> {
                    binding.tvInfo.text = ""
                    adapter.setNewData(it.data)

                    if(it.data.isEmpty()){
                        binding.tvInfo.text = "There is no data at this time"
                    }
                }
                is DataState.Error -> {
                    binding.tvInfo.text = it.exception.message
                }
            }

        }
    }

    private fun prepareRc(){
        adapter = LogsDetailAdapter(requireContext())

        binding.rcLogsDetail.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@LogsDetailFragment.adapter
        }
    }
}