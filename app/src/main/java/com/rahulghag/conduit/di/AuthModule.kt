package com.rahulghag.conduit.di

import android.content.Context
import com.rahulghag.conduit.data.remote.ConduitApi
import com.rahulghag.conduit.data.remote.TokenManager
import com.rahulghag.conduit.data.remote.TokenManagerImpl
import com.rahulghag.conduit.data.repositories.AuthRepositoryImpl
import com.rahulghag.conduit.domain.repositories.AuthRepository
import com.rahulghag.conduit.domain.usecases.SignInUseCase
import com.rahulghag.conduit.domain.usecases.SignUpUseCase
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
}