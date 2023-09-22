package com.artushock.apps.spillme.di

import android.content.Context
import androidx.room.Room
import com.artushock.apps.spillme.db.dao.PlantDao
import com.artushock.apps.spillme.db.db.SpillMeDatabase
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
    fun providePlantsDao(database: SpillMeDatabase): PlantDao {
        return database.plantDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context): SpillMeDatabase {
        return Room.databaseBuilder(
            applicationContext,
            SpillMeDatabase::class.java,
            "database"
        ).build()
    }
}