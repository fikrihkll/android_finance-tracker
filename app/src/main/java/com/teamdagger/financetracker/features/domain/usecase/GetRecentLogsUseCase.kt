package com.teamdagger.financetracker.features.domain.usecase

import com.synapsisid.smartdeskandroombooking.util.DataState
import com.teamdagger.financetracker.features.domain.entities.Logs
import com.teamdagger.financetracker.features.domain.repositories.FinanceRepository
import kotlinx.coroutines.flow.Flow

class GetRecentLogsUseCase(
    val repo: FinanceRepository
) {

    suspend fun execute(): Flow<DataState<List<Logs>>>{
        return repo.getRecentLogs()
    }

}