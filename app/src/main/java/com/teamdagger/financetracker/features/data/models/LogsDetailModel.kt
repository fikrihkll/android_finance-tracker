package com.teamdagger.financetracker.features.data.models

import androidx.room.ColumnInfo

data class LogsDetailModel (
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "total")
    val total:Long
)