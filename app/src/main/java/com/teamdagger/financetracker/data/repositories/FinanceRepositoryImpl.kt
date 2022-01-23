package com.teamdagger.financetracker.data.repositories

import com.synapsisid.smartdeskandroombooking.util.DataState
import com.teamdagger.financetracker.data.datasources.local.FinanceDao
import com.teamdagger.financetracker.data.models.LogsTableMapper
import com.teamdagger.financetracker.data.models.MonthExpenseMapper
import com.teamdagger.financetracker.domain.entities.Logs
import com.teamdagger.financetracker.domain.repositories.FinanceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FinanceRepositoryImpl(
    private val localDao: FinanceDao
) : FinanceRepository  {

    override suspend fun getExpenseInMonth(month: Int, year: Int) = flow {
        emit(DataState.Loading)

        try{
            coroutineScope {
                val response = async(Dispatchers.IO) { localDao.getMonthExpense(month, year) }
                val finalResponse = MonthExpenseMapper.entityToModel(response.await())

                emit(DataState.Success(finalResponse))
            }
        }catch (e:Exception){
            emit(DataState.Error(e))
        }
    }

    override suspend fun getRecentLogs() = flow {
        emit(DataState.Loading)

        try{
            coroutineScope {
                val response = async(Dispatchers.IO) { localDao.getLatestLogs() }
                val finalResponse = response.await().map { LogsTableMapper.entityToModel(it) }.toList()

                emit(DataState.Success(finalResponse))
            }
        }catch (e:Exception){
            emit(DataState.Error(e))
        }
    }

    override suspend fun insertLogs(data: Logs): DataState<Long>{
        return try {
            coroutineScope {
                val mappedData = LogsTableMapper.modelToEntity(data)
                val response = async(Dispatchers.IO) { localDao.insertLogs(mappedData) }

                delay(1000)

                return@coroutineScope DataState.Success(response.await())
            }
        } catch (e: Exception) {
            return DataState.Error(e)
        }
    }


}