package com.teamdagger.financetracker.data.datasources.local.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "users_table"
)
data class UsersTable(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name = "username")
    val username:String,
    @ColumnInfo(name = "pass")
    val pass:String,
    @ColumnInfo(name = "name")
    val name:String
)
