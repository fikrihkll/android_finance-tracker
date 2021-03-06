package com.teamdagger.financetracker.features.domain.repositories

import androidx.paging.PagingData
import com.synapsisid.smartdeskandroombooking.util.DataState
import com.teamdagger.financetracker.features.domain.entities.Logs
import com.teamdagger.financetracker.features.domain.entities.LogsDetail
import com.teamdagger.financetracker.features.domain.entities.MonthExpense
import kotlinx.coroutines.flow.Flow

interface FinanceRepository {

    suspend fun getExpenseInMonth(month: Int, year: Int): Flow<DataState<MonthExpense>>
    suspend fun getRecentLogs():Flow<DataState<List<Logs>>>
    fun getLogsInMonth(month: Int, year: Int):Flow<PagingData<Logs>>
    suspend fun insertLogs(data: Logs):DataState<Long>
    suspend fun deleteLog(id: Long):DataState<Int>
    suspend fun getLogsDetailInMonth(month: Int, year: Int):DataState<List<LogsDetail>>

}