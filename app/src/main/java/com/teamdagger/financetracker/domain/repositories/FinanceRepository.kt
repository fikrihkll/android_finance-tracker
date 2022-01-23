package com.teamdagger.financetracker.domain.repositories

import com.synapsisid.smartdeskandroombooking.util.DataState
import com.teamdagger.financetracker.domain.entities.Logs
import com.teamdagger.financetracker.domain.entities.MonthExpense
import kotlinx.coroutines.flow.Flow

interface FinanceRepository {

    suspend fun getExpenseInMonth(month: Int, year: Int): Flow<DataState<MonthExpense>>
    suspend fun getRecentLogs():Flow<DataState<List<Logs>>>
    suspend fun insertLogs(data: Logs):DataState<Long>

}