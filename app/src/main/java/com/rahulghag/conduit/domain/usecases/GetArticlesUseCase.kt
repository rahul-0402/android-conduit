package com.rahulghag.conduit.domain.usecases

import com.rahulghag.conduit.domain.models.Article
import com.rahulghag.conduit.domain.repositories.ArticlesRepository
import com.rahulghag.conduit.utils.ArticleSortType
import com.rahulghag.conduit.utils.Resource

class GetArticlesUseCase(
    private val articlesRepository: ArticlesRepository,
) {
    suspend operator fun invoke(articleSortType: ArticleSortType): Resource<List<Article>> {
        return articlesRepository.getArticles(articleSortType = articleSortType)
    }
}