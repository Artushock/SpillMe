package com.artushock.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun providePlantsDao(database: com.artushock.database.db.SpillMeDatabase) = database.plantDao()

    @Provides
    fun providePlantsTypeDao(database: com.artushock.database.db.SpillMeDatabase) = database.plantTypeDao()

    @Provides
    fun provideFertilizerDao(database: com.artushock.database.db.SpillMeDatabase) = database.fertilizerDao()

    @Provides
    fun provideFertilizerCareJoinDao(database: com.artushock.database.db.SpillMeDatabase) = database.fertilizerCareJoinDao()

    @Provides
    fun provideConditionsDao(database: com.artushock.database.db.SpillMeDatabase) = database.conditionsDao()

    @Provides
    fun provideCareFrequencyDao(database: com.artushock.database.db.SpillMeDatabase) = database.careFrequencyDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context): com.artushock.database.db.SpillMeDatabase {
        return Room.databaseBuilder(
            applicationContext,
            com.artushock.database.db.SpillMeDatabase::class.java,
            "database"
        ).build()
    }
}