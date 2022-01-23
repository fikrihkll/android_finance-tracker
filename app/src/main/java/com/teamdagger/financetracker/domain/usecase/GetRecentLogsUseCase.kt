package com.teamdagger.financetracker.domain.usecase

import com.synapsisid.smartdeskandroombooking.util.DataState
import com.teamdagger.financetracker.domain.entities.Logs
import com.teamdagger.financetracker.domain.repositories.FinanceRepository
import kotlinx.coroutines.flow.Flow

class GetRecentLogsUseCase(
    val repo: FinanceRepository
) {

    suspend fun execute(): Flow<DataState<List<Logs>>>{
        return repo.getRecentLogs()
    }

}