package com.rahulghag.conduit.features.articles.domain.usecases

import com.rahulghag.conduit.common.utils.Resource
import com.rahulghag.conduit.features.articles.domain.models.Article
import com.rahulghag.conduit.features.articles.domain.repositories.ArticlesRepository

class CreateArticleUseCase(
    private val articlesRepository: ArticlesRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String,
        body: String
    ): Resource<Article> {
        return articlesRepository.createArticle(
            title = title,
            description = description,
            body = body
        )
    }
}