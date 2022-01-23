package com.teamdagger.financetracker.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.teamdagger.financetracker.data.datasources.local.tables.LogsTable
import com.teamdagger.financetracker.data.datasources.local.tables.UsersTable

@Database(
    version = 2,
    entities = [
        UsersTable::class,
        LogsTable::class
    ]
)
abstract class FinanceDatabase :RoomDatabase() {

    abstract fun financeDao():FinanceDao

    companion object{
        val DB_NAME = "FINANCE_DB"
    }

}