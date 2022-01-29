package com.teamdagger.financetracker.presentation.log_list

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.synapsisid.smartdeskandroombooking.util.DataState
import com.teamdagger.financetracker.domain.entities.Logs
import com.teamdagger.financetracker.domain.entities.LogsDetail
import com.teamdagger.financetracker.domain.entities.MonthExpense
import com.teamdagger.financetracker.domain.usecase.DeleteLog
import com.teamdagger.financetracker.domain.usecase.GetExpenseInMonthUseCase
import com.teamdagger.financetracker.domain.usecase.GetLogsDetailInMonthUseCase
import com.teamdagger.financetracker.domain.usecase.GetLogsInMonthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class LogsListViewModel
@Inject
constructor(
    private val getLogsDetailInMonthUseCase: GetLogsDetailInMonthUseCase,
    private val getExpenseInMonthUseCase: GetExpenseInMonthUseCase,
    private val getLogsInMonthUseCase: GetLogsInMonthUseCase,
    private val deleteLog: DeleteLog,
    private val savedStateHandle: SavedStateHandle
):ViewModel(){

    private val _expenseStateEvent: MutableLiveData<DataState<MonthExpense>> = MutableLiveData()
    val expenseStateEvent: LiveData<DataState<MonthExpense>>
        get() = _expenseStateEvent

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

    fun setLogsInMonthStateEvent(month: Int, year: Int): Flow<PagingData<Logs>> {
        return getLogsInMonthUseCase.execute(month, year).cachedIn(viewModelScope)
    }

    fun setDeleteLogsStateEvent(id: Long){
        viewModelScope.launch {
            val response = async(Dispatchers.IO) { deleteLog.execute(id) }
            _deleteLogStateEvent.value = response.await()
        }
    }


}