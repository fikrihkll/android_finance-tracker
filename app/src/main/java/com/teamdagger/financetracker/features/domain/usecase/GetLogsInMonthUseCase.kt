package com.teamdagger.financetracker.features.domain.usecase

import androidx.paging.PagingData
import com.teamdagger.financetracker.features.domain.entities.Logs
import com.teamdagger.financetracker.features.domain.repositories.FinanceRepository
import kotlinx.coroutines.flow.Flow

class GetLogsInMonthUseCase(
    val repo: FinanceRepository
) {

    fun execute(month: Int, year: Int): Flow<PagingData<Logs>>{
        return repo.getLogsInMonth(month, year)
    }

}