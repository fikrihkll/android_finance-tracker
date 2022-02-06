package com.teamdagger.financetracker.features.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.teamdagger.financetracker.features.data.datasources.local.tables.LogsTable
import com.teamdagger.financetracker.features.data.datasources.local.tables.UsersTable
import com.teamdagger.financetracker.features.data.models.MonthExpenseModel
import com.teamdagger.financetracker.features.domain.entities.LogsDetail

@Dao
interface FinanceDao {

    @Insert
    suspend fun insertLogs(data: LogsTable):Long

    @Insert
    suspend fun createUser(data: UsersTable):Long

    @Query("SELECT*FROM logs_table ORDER BY date DESC LIMIT 10")
    suspend fun getLatestLogs():List<LogsTable>

    @Query("SELECT SUM(nominal) as total FROM logs_table WHERE month = :month AND year = :year")
    suspend fun getMonthExpense(month: Int, year: Int):MonthExpenseModel

    @Query("SELECT*FROM logs_table WHERE month = :month AND year = :year ORDER BY date DESC LIMIT 10 OFFSET :limit")
    suspend fun getLogsInMonth(month: Int, year: Int, limit: Int):List<LogsTable>

    @Query("DELETE FROM logs_table WHERE id = :id")
    suspend fun deleteLog(id: Long):Int

    @Query("SELECT category, SUM(nominal) as total FROM logs_table WHERE month = :month AND year = :year GROUP BY category")
    suspend fun getLogsDetailInMonth(month: Int, year: Int,):List<LogsDetail>

}