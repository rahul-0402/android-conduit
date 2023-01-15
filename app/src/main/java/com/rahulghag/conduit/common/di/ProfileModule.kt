package com.rahulghag.conduit.common.di

import com.rahulghag.conduit.common.data.remote.ConduitApi
import com.rahulghag.conduit.common.data.repositories.ProfileRepositoryImpl
import com.rahulghag.conduit.common.domain.repositories.ProfileRepository
import com.rahulghag.conduit.common.domain.usecases.ToggleFollowUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {
    @Provides
    @Singleton
    fun provideProfileRepository(conduitApi: ConduitApi): ProfileRepository {
        return ProfileRepositoryImpl(conduitApi)
    }

    @Provides
    @Singleton
    fun provideFollowUserUseCase(profileRepository: ProfileRepository): ToggleFollowUserUseCase {
        return ToggleFollowUserUseCase(profileRepository)
    }
}