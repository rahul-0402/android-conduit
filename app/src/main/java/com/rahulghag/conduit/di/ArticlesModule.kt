package com.rahulghag.conduit.di

import com.rahulghag.conduit.data.remote.ConduitApi
import com.rahulghag.conduit.data.repositories.ArticlesRepositoryImpl
import com.rahulghag.conduit.domain.repositories.ArticlesRepository
import com.rahulghag.conduit.domain.usecases.GetArticleUseCase
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
    fun provideGetArticlesUseCase(articlesRepository: ArticlesRepository): GetArticleUseCase {
        return GetArticleUseCase(articlesRepository)
    }
}