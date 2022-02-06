package com.teamdagger.financetracker.features.data.datasources.local.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "logs_table"
)
data class LogsTable(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "desc")
    val desc: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "month")
    val month: Int,
    @ColumnInfo(name = "year")
    val year: Int,
    @ColumnInfo(name = "nominal")
    val nominal: Long,
    @ColumnInfo(name = "user_id")
    val userId: Int
)