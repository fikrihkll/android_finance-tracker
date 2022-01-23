package com.teamdagger.financetracker.domain.entities

data class Logs(
    val id: Long,
    val category: String,
    val desc: String,
    val date: String,
    val month: Int,
    val year: Int,
    val nominal: Long,
    val userId: Int
)