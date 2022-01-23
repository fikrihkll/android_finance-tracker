package com.teamdagger.financetracker.domain.usecase

import com.synapsisid.smartdeskandroombooking.util.DataState
import com.teamdagger.financetracker.domain.entities.MonthExpense
import com.teamdagger.financetracker.domain.repositories.FinanceRepository
import kotlinx.coroutines.flow.Flow

class GetExpenseInMonthUseCase(
    private val repo: FinanceRepository
) {

    suspend fun execute(month: Int, year: Int): Flow<DataState<MonthExpense>> {
        return repo.getExpenseInMonth(month, year)
    }

}