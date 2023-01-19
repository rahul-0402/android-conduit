package com.rahulghag.conduit.di

import com.rahulghag.conduit.data.remote.ConduitApi
import com.rahulghag.conduit.data.repositories.ArticlesRepositoryImpl
import com.rahulghag.conduit.domain.repositories.ArticlesRepository
import com.rahulghag.conduit.domain.usecases.CreateArticleUseCase
import com.rahulghag.conduit.domain.usecases.GetArticleUseCase
import com.rahulghag.conduit.domain.usecases.GetArticlesUseCase
import com.rahulghag.conduit.domain.usecases.ToggleFavoriteArticleUseCase
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
    fun provideArticlesRepository(conduitApi: ConduitApi): ArticlesRepository {
        return ArticlesRepositoryImpl(conduitApi)
    }

    @Provides
    @Singleton
    fun provideGetArticlesUseCase(articlesRepository: ArticlesRepository): GetArticlesUseCase {
        return GetArticlesUseCase(articlesRepository)
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