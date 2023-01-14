package com.rahulghag.conduit.features.articles.domain.usecases

import com.rahulghag.conduit.common.utils.Resource
import com.rahulghag.conduit.features.articles.domain.models.Article
import com.rahulghag.conduit.features.articles.domain.repositories.ArticlesRepository

class GetArticlesUseCase(
    private val articlesRepository: ArticlesRepository,
) {
    suspend operator fun invoke(): Resource<List<Article>> {
        return articlesRepository.getArticles()
    }
}