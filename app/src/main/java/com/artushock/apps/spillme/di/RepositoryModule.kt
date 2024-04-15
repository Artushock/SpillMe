package com.artushock.apps.spillme.di


import com.artushock.apps.spillme.db.dao.CareFrequencyDao
import com.artushock.apps.spillme.db.dao.ConditionsDao
import com.artushock.apps.spillme.db.dao.FertilizerCareJoinDao
import com.artushock.apps.spillme.db.dao.FertilizerDao
import com.artushock.apps.spillme.db.dao.PlantDao
import com.artushock.apps.spillme.db.dao.PlantTypeDao
import com.artushock.apps.spillme.repositories.PlantRepository
import com.artushock.apps.spillme.repositories.AuthRepository
import com.artushock.apps.spillme.repositories.AuthRepositoryImpl
import dagger.Binds
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
        fertilizerDao: FertilizerDao,
        careFrequencyDao: CareFrequencyDao,
        fertilizerCareJoinDao: FertilizerCareJoinDao,
        conditionsDao: ConditionsDao,
        plantTypeDao: PlantTypeDao,
    ): PlantRepository {
        return PlantRepository(
            plantDao,
            fertilizerDao,
            careFrequencyDao,
            fertilizerCareJoinDao,
            conditionsDao,
            plantTypeDao,
        )
    }
}

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryBindModule {
    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}