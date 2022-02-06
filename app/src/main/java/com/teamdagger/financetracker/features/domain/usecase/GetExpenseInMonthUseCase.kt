package com.teamdagger.financetracker.features.domain.usecase

import com.synapsisid.smartdeskandroombooking.util.DataState
import com.teamdagger.financetracker.features.domain.entities.MonthExpense
import com.teamdagger.financetracker.features.domain.repositories.FinanceRepository
import kotlinx.coroutines.flow.Flow

class GetExpenseInMonthUseCase(
    private val repo: FinanceRepository
) {

    suspend fun execute(month: Int, year: Int): Flow<DataState<MonthExpense>> {
        return repo.getExpenseInMonth(month, year)
    }

}