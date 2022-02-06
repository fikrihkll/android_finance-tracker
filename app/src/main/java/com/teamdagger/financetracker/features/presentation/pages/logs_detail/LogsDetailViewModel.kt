package com.teamdagger.financetracker.features.presentation.pages.logs_detail

import androidx.lifecycle.*
import com.synapsisid.smartdeskandroombooking.util.DataState
import com.teamdagger.financetracker.features.domain.entities.LogsDetail
import com.teamdagger.financetracker.features.domain.usecase.GetLogsDetailInMonthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class LogsDetailViewModel
@Inject
constructor(
    private val getLogsDetailInMonthUseCase: GetLogsDetailInMonthUseCase,
    private val savedStateHandle: SavedStateHandle
):ViewModel(){

    private val _logsDetailStateEvent: MutableLiveData<DataState<List<LogsDetail>>> = MutableLiveData()
    val logsDetailStateEvent: LiveData<DataState<List<LogsDetail>>>
        get() = _logsDetailStateEvent

    fun setLogsDetailStateEvent(month: Int, year: Int){
        viewModelScope.launch {
            val response = async(Dispatchers.IO) { getLogsDetailInMonthUseCase.execute(month, year) }
            _logsDetailStateEvent.value = response.await()
        }
    }

}