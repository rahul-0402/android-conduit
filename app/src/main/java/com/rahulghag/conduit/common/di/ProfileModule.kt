package com.rahulghag.conduit.common.di

import com.rahulghag.conduit.common.data.remote.ConduitApi
import com.rahulghag.conduit.common.data.repositories.ProfileRepositoryImpl
import com.rahulghag.conduit.common.domain.repositories.ProfileRepository
import com.rahulghag.conduit.common.domain.usecases.FollowUserUseCase
import com.rahulghag.conduit.common.domain.usecases.UnfollowUserUseCase
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
    fun provideFollowUserUseCase(profileRepository: ProfileRepository): FollowUserUseCase {
        return FollowUserUseCase(profileRepository)
    }

    @Provides
    @Singleton
    fun provideUnfollowUserUseCase(profileRepository: ProfileRepository): UnfollowUserUseCase {
        return UnfollowUserUseCase(profileRepository)
    }
}