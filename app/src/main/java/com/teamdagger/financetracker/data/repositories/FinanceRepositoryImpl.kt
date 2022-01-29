package com.teamdagger.financetracker.data.repositories

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.synapsisid.smartdeskandroombooking.util.DataState
import com.teamdagger.financetracker.data.datasources.local.FinanceDao
import com.teamdagger.financetracker.data.datasources.paging_sources.LogsListPagingSource
import com.teamdagger.financetracker.data.models.LogsTableMapper
import com.teamdagger.financetracker.data.models.MonthExpenseMapper
import com.teamdagger.financetracker.domain.entities.Logs
import com.teamdagger.financetracker.domain.entities.LogsDetail
import com.teamdagger.financetracker.domain.repositories.FinanceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FinanceRepositoryImpl(
    private val context: Context,
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

    override fun getLogsInMonth(month: Int, year: Int): Flow<PagingData<Logs>> {
        return Pager(
            config = PagingConfig(
                pageSize = LogsListPagingSource.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { LogsListPagingSource(context, localDao, month, year) }
        ).flow
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

    override suspend fun deleteLog(id: Long): DataState<Int> {
        return try {
            coroutineScope {
                val response = async(Dispatchers.IO) { localDao.deleteLog(id) }

                delay(1000)

                return@coroutineScope DataState.Success(response.await())
            }
        } catch (e: Exception) {
            return DataState.Error(e)
        }
    }

    override suspend fun getLogsDetailInMonth(month: Int, year: Int): DataState<List<LogsDetail>> {
        return try{
            coroutineScope {
                val response = async(Dispatchers.IO) { localDao.getLogsDetailInMonth(month, year) }

                return@coroutineScope DataState.Success(response.await())
            }
        }catch (e:Exception){
            return DataState.Error(e)
        }
    }


}