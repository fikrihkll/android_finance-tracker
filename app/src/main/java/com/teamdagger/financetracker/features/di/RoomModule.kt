package com.teamdagger.financetracker.features.di

import android.content.Context
import androidx.room.Room
import com.teamdagger.financetracker.features.data.datasources.local.FinanceDao
import com.teamdagger.financetracker.features.data.datasources.local.FinanceDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {
    @Singleton
    @Provides
    fun provideSynapsisDb(@ApplicationContext context: Context): FinanceDatabase {
        return Room.databaseBuilder(
            context,
            FinanceDatabase::class.java,
            FinanceDatabase.DB_NAME,
        ).fallbackToDestructiveMigration().build()
    }


//    DAO MODULE -------------

    @Singleton
    @Provides
    fun provideBookingDAO(localDb: FinanceDatabase):FinanceDao{
        return localDb.financeDao()
    }

//    End of DAO MODULE ------



}