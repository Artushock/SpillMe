package com.artushock.apps.spillme.di

import com.artushock.apps.spillme.db.dao.PlantDao
import com.artushock.apps.spillme.repositories.PlantRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providePlantRepository(
        plantDao: PlantDao,
    ): PlantRepository {
        return PlantRepository(plantDao)
    }
}