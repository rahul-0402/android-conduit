package com.rahulghag.conduit.di

import android.content.Context
import com.rahulghag.conduit.data.remote.ConduitApi
import com.rahulghag.conduit.data.repositories.AuthRepositoryImpl
import com.rahulghag.conduit.data.repositories.TokenManagerImpl
import com.rahulghag.conduit.domain.repositories.AuthRepository
import com.rahulghag.conduit.domain.repositories.PreferencesManager
import com.rahulghag.conduit.domain.repositories.TokenManager
import com.rahulghag.conduit.domain.usecases.GetUserAuthStateUseCase
import com.rahulghag.conduit.domain.usecases.LogoutUserUseCase
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
        return TokenManagerImpl(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        conduitApi: ConduitApi,
        tokenManager: TokenManager,
        preferencesManager: PreferencesManager
    ): AuthRepository {
        return AuthRepositoryImpl(conduitApi, tokenManager, preferencesManager)
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
        return GetUserAuthStateUseCase(tokenManager)
    }

    @Provides
    @Singleton
    fun provideLogoutUserUseCase(
        preferencesManager: PreferencesManager,
        tokenManager: TokenManager
    ): LogoutUserUseCase {
        return LogoutUserUseCase(preferencesManager, tokenManager)
    }
}