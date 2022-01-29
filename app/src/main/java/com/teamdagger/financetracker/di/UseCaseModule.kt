package com.teamdagger.financetracker.di

import com.teamdagger.financetracker.domain.repositories.FinanceRepository
import com.teamdagger.financetracker.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ActivityScoped
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