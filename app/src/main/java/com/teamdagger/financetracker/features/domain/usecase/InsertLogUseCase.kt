package com.teamdagger.financetracker.features.domain.usecase

import com.synapsisid.smartdeskandroombooking.util.DataState
import com.teamdagger.financetracker.features.domain.entities.Logs
import com.teamdagger.financetracker.features.domain.repositories.FinanceRepository

class InsertLogUseCase(
    private val repo: FinanceRepository
) {

    suspend fun execute(data: Logs): DataState<Long>{
        return repo.insertLogs(data)
    }

}