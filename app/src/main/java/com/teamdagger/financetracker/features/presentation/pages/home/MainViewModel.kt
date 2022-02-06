package com.teamdagger.financetracker.features.presentation.pages.home

import androidx.lifecycle.*
import com.synapsisid.smartdeskandroombooking.util.DataState
import com.teamdagger.financetracker.features.domain.entities.Logs
import com.teamdagger.financetracker.features.domain.entities.MonthExpense
import com.teamdagger.financetracker.features.domain.usecase.DeleteLog
import com.teamdagger.financetracker.features.domain.usecase.GetExpenseInMonthUseCase
import com.teamdagger.financetracker.features.domain.usecase.GetRecentLogsUseCase
import com.teamdagger.financetracker.features.domain.usecase.InsertLogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class MainViewModel
@Inject
constructor(
    private val getExpenseInMonthUseCase: GetExpenseInMonthUseCase,
    private val getRecentLogsUseCase: GetRecentLogsUseCase,
    private val insertLogUseCase: InsertLogUseCase,
    private val deleteLog: DeleteLog,
    private val savedStateHandle: SavedStateHandle
):ViewModel() {

    private val _expenseStateEvent: MutableLiveData<DataState<MonthExpense>> = MutableLiveData()
    val expenseStateEvent: LiveData<DataState<MonthExpense>>
    get() = _expenseStateEvent

    private val _recentLogsStateEvent: MutableLiveData<DataState<List<Logs>>> = MutableLiveData()
    val recentLogsStateEvent: LiveData<DataState<List<Logs>>>
        get() = _recentLogsStateEvent

    private val _insertLogStateEvent: MutableLiveData<DataState<Long>> = MutableLiveData()
    val insertLogStateEvent: LiveData<DataState<Long>>
        get() = _insertLogStateEvent

    private val _deleteLogStateEvent: MutableLiveData<DataState<Int>> = MutableLiveData()
    val deleteLogStateEvent: LiveData<DataState<Int>>
        get() = _deleteLogStateEvent

    fun setExpenseState(month: Int, year: Int){
        viewModelScope.launch {
            getExpenseInMonthUseCase.execute(month, year).onEach {
                _expenseStateEvent.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun insertLog(data: Logs){
        viewModelScope.launch {
            coroutineScope {
                val response = async(Dispatchers.IO) { insertLogUseCase.execute(data) }
                _insertLogStateEvent.value =  response.await()
            }
        }
    }

    fun setRecentLogsState(){
        viewModelScope.launch {
            getRecentLogsUseCase.execute().onEach {
                _recentLogsStateEvent.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun deleteLog(id: Long){
        viewModelScope.launch {
            coroutineScope {
                val response = async(Dispatchers.IO) { deleteLog.execute(id) }
                _deleteLogStateEvent.value = response.await()
            }
        }
    }

}