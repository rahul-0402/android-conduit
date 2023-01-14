package com.rahulghag.conduit.common.di

import android.content.Context
import com.rahulghag.conduit.common.data.remote.ConduitApi
import com.rahulghag.conduit.features.articles.data.repositories.AuthRepositoryImpl
import com.rahulghag.conduit.features.auth.data.remote.TokenManager
import com.rahulghag.conduit.features.auth.data.remote.TokenManagerImpl
import com.rahulghag.conduit.features.auth.domain.repositories.AuthRepository
import com.rahulghag.conduit.features.auth.domain.usecases.GetUserAuthStateUseCase
import com.rahulghag.conduit.features.auth.domain.usecases.SignInUseCase
import com.rahulghag.conduit.features.auth.domain.usecases.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManagerImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(conduitApi: ConduitApi, tokenManager: TokenManager): AuthRepository {
        return AuthRepositoryImpl(conduitApi, tokenManager)
    }

    @Provides
    @Singleton
    fun provideSignInUseCase(authRepository: AuthRepository): SignInUseCase {
        return SignInUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideSignUpUseCase(authRepository: AuthRepository): SignUpUseCase {
        return SignUpUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideGetUserAuthStateUseCase(tokenManager: TokenManager): GetUserAuthStateUseCase {
        return GetUserAuthStateUseCase(tokenManager = tokenManager)
    }
}