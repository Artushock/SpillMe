package com.artushock.apps.spillme.di

import com.artushock.apps.spillme.services.AuthService
import com.artushock.apps.spillme.services.AuthServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkServiceModule {
    @Binds
    abstract fun provideAuthService(impl: AuthServiceImpl): AuthService
}