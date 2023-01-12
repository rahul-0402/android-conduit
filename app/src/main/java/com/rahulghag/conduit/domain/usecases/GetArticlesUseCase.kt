package com.rahulghag.conduit.domain.usecases

import com.rahulghag.conduit.common.Resource
import com.rahulghag.conduit.domain.models.Article
import com.rahulghag.conduit.domain.repositories.ArticlesRepository

class GetArticlesUseCase(
    private val articlesRepository: ArticlesRepository,
) {
    suspend operator fun invoke(): Resource<List<Article>> {
        return articlesRepository.getArticles()
    }
}