package com.teamdagger.financetracker.di

import com.teamdagger.financetracker.data.datasources.local.FinanceDao
import com.teamdagger.financetracker.data.repositories.FinanceRepositoryImpl
import com.teamdagger.financetracker.domain.repositories.FinanceRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    fun provideFinanceRepo(financeDao: FinanceDao): FinanceRepository {
        return FinanceRepositoryImpl(financeDao)
    }
}