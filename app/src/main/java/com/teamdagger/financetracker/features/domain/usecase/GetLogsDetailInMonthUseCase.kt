package com.teamdagger.financetracker.features.domain.usecase

import com.synapsisid.smartdeskandroombooking.util.DataState
import com.teamdagger.financetracker.features.domain.entities.LogsDetail
import com.teamdagger.financetracker.features.domain.repositories.FinanceRepository

class GetLogsDetailInMonthUseCase (private val repo: FinanceRepository){

    suspend fun execute(month: Int, year: Int):DataState<List<LogsDetail>>{
        return repo.getLogsDetailInMonth(month, year)
    }

}