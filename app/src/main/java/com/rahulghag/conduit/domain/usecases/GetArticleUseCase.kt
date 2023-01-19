package com.rahulghag.conduit.domain.usecases

import com.rahulghag.conduit.domain.models.Article
import com.rahulghag.conduit.domain.repositories.ArticlesRepository
import com.rahulghag.conduit.utils.Resource

class GetArticleUseCase(
    private val articlesRepository: ArticlesRepository,
) {
    suspend operator fun invoke(slug: String): Resource<Article> {
        return articlesRepository.getArticle(slug = slug)
    }
}