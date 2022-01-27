package com.teamdagger.financetracker.domain.usecase

import androidx.paging.PagingData
import com.synapsisid.smartdeskandroombooking.util.DataState
import com.teamdagger.financetracker.domain.entities.Logs
import com.teamdagger.financetracker.domain.repositories.FinanceRepository
import kotlinx.coroutines.flow.Flow

class GetLogsInMonthUseCase(
    val repo: FinanceRepository
) {

    fun execute(month: Int, year: Int): Flow<PagingData<Logs>>{
        return repo.getLogsInMonth(month, year)
    }

}