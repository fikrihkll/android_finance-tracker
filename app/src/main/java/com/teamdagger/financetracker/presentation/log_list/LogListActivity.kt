package com.teamdagger.financetracker.presentation.log_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.HttpException
import com.synapsisid.smartdeskandroombooking.util.DataState
import com.synapsisid.smartdeskandroombooking.util.Util
import com.teamdagger.financetracker.R
import com.teamdagger.financetracker.databinding.ActivityLogListBinding
import com.teamdagger.financetracker.presentation.logs_detail.LogsDetailFragment
import com.teamdagger.financetracker.presentation.paging_item_load_state.ListLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class LogListActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var binding: ActivityLogListBinding

    private lateinit var adapter: LogsAdapter

    private var logsListJob: Job?=null

    val viewModel: LogsListViewModel by viewModels()

    private lateinit var adapterMonth : ArrayAdapter<String>
    private lateinit var adapterYear : ArrayAdapter<String>

    private var listYear = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRc()
        subscribeLogsList(null,null)
        subscribeExpense()
        subscribeDelete()

        generateYear()
        prepareSpinnerCategory()

        onClick()

        val now = Calendar.getInstance()
        viewModel.setExpenseState(now.get(Calendar.MONTH)+1, now.get(Calendar.YEAR))
    }

    private fun onClick(){
        binding.toolbarLog.setNavigationOnClickListener {
            this.finish()
        }

        binding.toolbarLog.setOnMenuItemClickListener {

            when(it.itemId){
                R.id.m_log_detail ->{

                    val dl = LogsDetailFragment(binding.spMonth.selectedItemPosition+1, Integer.parseInt(binding.spYear.selectedItem.toString()))
                    dl.show(supportFragmentManager, LogsDetailFragment::class.simpleName)

                }
            }

            true
        }
    }

    private fun subscribeDelete(){
        viewModel.deleteLogStateEvent.observe(this) {
            when (it) {
                is DataState.Success -> {
                    val now = Calendar.getInstance()
                    viewModel.setExpenseState(now.get(Calendar.MONTH) + 1, now.get(Calendar.YEAR))
                }
            }
        }
    }

    private fun subscribeExpense(){
        viewModel.expenseStateEvent.observe(this, {
            when(it){
                is DataState.Success ->{
                    binding.tvExpenseLog.text = "Rp.${it.data.total}"
                }
            }
        })
    }

    private fun generateYear(){
        val now = Calendar.getInstance()
        listYear.add(now.get(Calendar.YEAR))
        listYear.add(now.get(Calendar.YEAR)-1)
        listYear.add(now.get(Calendar.YEAR)-2)
        listYear.add(now.get(Calendar.YEAR)-3)
    }

    private fun prepareSpinnerCategory(){
        val now = Calendar.getInstance()

        adapterMonth = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Util.listMonth)
        binding.spMonth.adapter = adapterMonth
        binding.spMonth.setSelection(now.get(Calendar.MONTH))

        adapterYear = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listYear.map { it.toString() }.toList())
        binding.spYear.adapter = adapterYear

        binding.spMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                subscribeLogsList(p2+1, listYear[binding.spYear.selectedItemPosition])
                viewModel.setExpenseState(p2+1, listYear[binding.spYear.selectedItemPosition])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.spYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                subscribeLogsList(binding.spMonth.selectedItemPosition+1, listYear[p2])
                viewModel.setExpenseState(binding.spMonth.selectedItemPosition+1, listYear[p2])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    private fun subscribeLogsList(month: Int?, year:Int?){
        val now = Calendar.getInstance()

        lifecycleScope.launch {
            if(month == null && year == null){
                viewModel.setLogsInMonthStateEvent(now.get(Calendar.MONTH)+1, now.get(Calendar.YEAR)).collectLatest {
                    adapter.submitData(it)
                }
            }else{
                viewModel.setLogsInMonthStateEvent(month!!, year!!).collectLatest {
                    adapter.submitData(it)
                }
            }

        }
    }

    private fun prepareRc(){
        adapter = LogsAdapter(this)
        binding.rcLogs.layoutManager = LinearLayoutManager(this)
        binding.rcLogs.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ListLoadStateAdapter { adapter.retry() },
            footer = ListLoadStateAdapter { adapter.retry() }
        )

        adapter.addLoadStateListener {
            binding.rcLogs.isVisible = it.source.refresh is LoadState.NotLoading
            binding.pgbLogList.isVisible = it.source.refresh is LoadState.Loading
            binding.btnReload.isVisible = it.source.refresh is LoadState.Error
            if(it.source.refresh is LoadState.Error){
                Toast.makeText(this, "Data can't be fetched, try again.", Toast.LENGTH_LONG).show()
                val err = (it.source.refresh as LoadState.Error).error
                if(err is HttpException) {

                }

            }

            val errorState = it.source.append as? LoadState.Error
                ?: it.source.prepend as? LoadState.Error
                ?: it.append as? LoadState.Error
                ?: it.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    this,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        adapter.onLongClick {logs, pos ->
            AlertDialog.Builder(this).setMessage("Delete This Log?").setPositiveButton("Yea") { _, _ ->
                lifecycleScope.launch(Dispatchers.Main) {
                    var data = adapter.snapshot().items.filter { logs.id != it.id }
                    adapter.submitData(PagingData.from(data))
                    viewModel.setDeleteLogsStateEvent(logs.id)
                }
            }.setNegativeButton("Nope") {_,_ -> }.show()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        logsListJob?.cancel()
    }

    override val coroutineContext: CoroutineContext
        get() = logsListJob!!

}