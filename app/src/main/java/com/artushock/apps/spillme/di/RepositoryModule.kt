package com.artushock.apps.spillme.di

import com.artushock.apps.spillme.repositories.AuthRepository
import com.artushock.apps.spillme.repositories.AuthRepositoryImpl
import com.artushock.apps.spillme.repositories.PlantRepository
import com.artushock.apps.spillme.repositories.PlantRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
interface RepositoryBindModule {
    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindPlantRepository(impl: PlantRepositoryImpl): PlantRepository
}