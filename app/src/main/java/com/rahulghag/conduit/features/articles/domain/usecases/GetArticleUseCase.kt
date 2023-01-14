package com.rahulghag.conduit.features.articles.domain.usecases

import com.rahulghag.conduit.common.utils.Resource
import com.rahulghag.conduit.features.articles.domain.models.Article
import com.rahulghag.conduit.features.articles.domain.repositories.ArticlesRepository

class GetArticleUseCase(
    private val articlesRepository: ArticlesRepository,
) {
    suspend operator fun invoke(slug: String): Resource<Article> {
        return articlesRepository.getArticle(slug = slug)
    }
}