package com.teamdagger.financetracker.features.domain.usecase

import com.synapsisid.smartdeskandroombooking.util.DataState
import com.teamdagger.financetracker.features.domain.repositories.FinanceRepository

class DeleteLog(
    private val repo: FinanceRepository
) {

    suspend fun execute(id: Long):DataState<Int>{
        return repo.deleteLog(id)
    }

}