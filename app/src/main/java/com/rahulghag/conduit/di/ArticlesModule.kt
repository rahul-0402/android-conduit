package com.rahulghag.conduit.di

import com.rahulghag.conduit.data.remote.ConduitApi
import com.rahulghag.conduit.data.repositories.ArticlesRepositoryImpl
import com.rahulghag.conduit.domain.repositories.ArticlesRepository
import com.rahulghag.conduit.domain.repositories.PreferencesManager
import com.rahulghag.conduit.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ArticlesModule {
    @Provides
    @Singleton
    fun provideArticlesRepository(
        conduitApi: ConduitApi,
        preferencesManager: PreferencesManager
    ): ArticlesRepository {
        return ArticlesRepositoryImpl(conduitApi, preferencesManager)
    }

    @Provides
    @Singleton
    fun provideGetArticlesUseCase(articlesRepository: ArticlesRepository): GetArticlesUseCase {
        return GetArticlesUseCase(articlesRepository)
    }

    @Provides
    @Singleton
    fun provideGetArticlesByUsernameUseCase(articlesRepository: ArticlesRepository): GetArticlesByUsernameUseCase {
        return GetArticlesByUsernameUseCase(articlesRepository)
    }

    @Provides
    @Singleton
    fun provideGetFavoritedArticlesByUsernameUseCase(articlesRepository: ArticlesRepository): GetFavoritedArticlesByUsernameUseCase {
        return GetFavoritedArticlesByUsernameUseCase(articlesRepository)
    }

    @Provides
    @Singleton
    fun provideGetArticleUseCase(articlesRepository: ArticlesRepository): GetArticleUseCase {
        return GetArticleUseCase(articlesRepository)
    }

    @Provides
    @Singleton
    fun provideToggleFavoriteArticleUseCase(articlesRepository: ArticlesRepository): ToggleFavoriteArticleUseCase {
        return ToggleFavoriteArticleUseCase(articlesRepository)
    }

    @Provides
    @Singleton
    fun provideCreateArticleUseCase(articlesRepository: ArticlesRepository): CreateArticleUseCase {
        return CreateArticleUseCase(articlesRepository)
    }
}