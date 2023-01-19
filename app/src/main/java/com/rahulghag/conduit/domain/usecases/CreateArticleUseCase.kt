package com.rahulghag.conduit.domain.usecases

import com.rahulghag.conduit.domain.models.Article
import com.rahulghag.conduit.domain.repositories.ArticlesRepository
import com.rahulghag.conduit.utils.Resource

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