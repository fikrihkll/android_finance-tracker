package com.teamdagger.financetracker.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.teamdagger.financetracker.data.datasources.local.tables.LogsTable
import com.teamdagger.financetracker.data.datasources.local.tables.UsersTable
import com.teamdagger.financetracker.data.models.MonthExpenseModel

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

    @Query("SELECT*FROM logs_table WHERE month = :month AND year = :year ORDER BY date DESC")
    suspend fun getLogsInMonth(month: Int, year: Int ):List<LogsTable>

}