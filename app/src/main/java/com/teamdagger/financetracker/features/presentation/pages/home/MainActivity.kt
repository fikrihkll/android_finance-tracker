package com.teamdagger.financetracker.features.presentation.pages.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.synapsisid.smartdeskandroombooking.util.DataState
import com.synapsisid.smartdeskandroombooking.util.Util
import com.teamdagger.financetracker.databinding.ActivityMainBinding
import com.teamdagger.financetracker.features.domain.entities.Logs
import com.teamdagger.financetracker.features.presentation.pages.log_list.LogListActivity
import com.teamdagger.financetracker.features.presentation.widget.month_selector.MonthSelectorFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapterCatgory : ArrayAdapter<String>

    val viewModel : MainViewModel by viewModels()

    private val cal = Calendar.getInstance()

    private lateinit var adapterRc : RecentLogsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareSpinnerCategory()
        subscribeExpense()
        subscribeInsertLog()
        subscribeRecentLogs()
        subscribeDeleteLogs()
        prepareRc()
        onClick()

        viewModel.setExpenseState(cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR))
        viewModel.setRecentLogsState()
    }

    private fun prepareRc(){
        adapterRc = RecentLogsAdapter(this)
        binding.rcRecentLogs.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterRc
        }

        adapterRc.onLongClick{
            AlertDialog.Builder(this).setMessage("Delete This Log?").setPositiveButton("Yea") { _, _ ->
                viewModel.deleteLog(it)
            }.setNegativeButton("Nope") {_,_ -> }.show()
        }
    }

    private fun setButtonLoading(){
        binding.btnSaveNominal.text=""
        binding.btnSaveNominal.isEnabled = false
        binding.pgbSaveNominal.visibility = View.VISIBLE
    }
    private fun setDefaultButton(){
        binding.btnSaveNominal.text="Save"
        binding.btnSaveNominal.isEnabled = true
        binding.pgbSaveNominal.visibility = View.GONE
    }

    private fun onClick(){
        binding.refreshMain.setOnRefreshListener {
            viewModel.setRecentLogsState()
            viewModel.setExpenseState(cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR))
        }

        binding.btnAllLogs.setOnClickListener {
            startActivity(Intent(this, LogListActivity::class.java))
        }

        binding.btnSaveNominal.setOnClickListener {
            if(validate()){
                setButtonLoading()
                viewModel.insertLog(Logs(
                    0,
                    Util.listCategory[binding.spCategory.selectedItemPosition],
                    if(binding.etDesc.text.isEmpty()) "-" else binding.etDesc.text.toString(),
                    Util.sdf.format(Calendar.getInstance().time),
                    Calendar.getInstance().get(Calendar.MONTH)+1,
                    Calendar.getInstance().get(Calendar.YEAR),
                    binding.etNominal.text.toString().toLong(),
                    Util.getUserID(this)
                ))
            }
        }
    }

    private fun validate():Boolean{
        var isValidated = true

        if(binding.etNominal.text.isEmpty()){
            isValidated = false
        }

        return isValidated
    }

    private fun subscribeRecentLogs(){
        viewModel.recentLogsStateEvent.observe(this) {
            when (it) {
                is DataState.Success -> {
                    adapterRc.setNewData(it.data)
                    binding.refreshMain.isRefreshing = false
                }
            }
        }
    }

    private fun subscribeInsertLog(){
        viewModel.insertLogStateEvent.observe(this) {
            when (it) {
                is DataState.Success -> {
                    setDefaultButton()
                    viewModel.setExpenseState(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR))
                    viewModel.setRecentLogsState()
                }
            }
        }
    }

    private fun subscribeExpense(){
        viewModel.expenseStateEvent.observe(this) {
            when (it) {
                is DataState.Success -> {
                    binding.tvExpense.text = "Rp.${it.data.total}"
                }
            }
        }
    }

    private fun subscribeDeleteLogs() {
        viewModel.deleteLogStateEvent.observe(this) {
            when (it) {
                is DataState.Success -> {
                    viewModel.setExpenseState(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR))
                    viewModel.setRecentLogsState()
                }
            }
        }
    }

    private fun prepareSpinnerCategory(){
        adapterCatgory = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Util.listCategory)
        binding.spCategory.adapter = adapterCatgory
    }
}