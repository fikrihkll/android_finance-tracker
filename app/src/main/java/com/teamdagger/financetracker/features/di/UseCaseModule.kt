package com.teamdagger.financetracker.features.di

import com.teamdagger.financetracker.features.domain.repositories.FinanceRepository
import com.teamdagger.financetracker.features.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @Provides
    fun provideGetExpenseMonthUseCase(repo: FinanceRepository): GetExpenseInMonthUseCase {
        return GetExpenseInMonthUseCase(repo)
    }

    @Provides
    fun provideGetRecentLogsUseCase(repo: FinanceRepository): GetRecentLogsUseCase {
        return GetRecentLogsUseCase(repo)
    }

    @Provides
    fun provideInsertLogUseCase(repo: FinanceRepository): InsertLogUseCase {
        return InsertLogUseCase(repo)
    }

    @Provides
    fun provideDeleteLogUseCase(repo: FinanceRepository): DeleteLog {
        return DeleteLog(repo)
    }

    @Provides
    fun provideLogsInMonthUseCase(repo: FinanceRepository): GetLogsInMonthUseCase {
        return GetLogsInMonthUseCase(repo)
    }

    @Provides
    fun provideLogsDetailInMonthUseCase(repo: FinanceRepository): GetLogsDetailInMonthUseCase {
        return GetLogsDetailInMonthUseCase(repo)
    }
}