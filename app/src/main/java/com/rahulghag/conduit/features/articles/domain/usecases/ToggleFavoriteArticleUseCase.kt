package com.rahulghag.conduit.features.articles.domain.usecases

import com.rahulghag.conduit.common.utils.Resource
import com.rahulghag.conduit.features.articles.domain.models.Article
import com.rahulghag.conduit.features.articles.domain.repositories.ArticlesRepository

class ToggleFavoriteArticleUseCase(
    private val articlesRepository: ArticlesRepository
) {
    suspend operator fun invoke(isFavorite: Boolean, slug: String): Resource<Article> {
        return if (isFavorite) {
            articlesRepository.removeArticleFromFavorites(slug = slug)
        } else {
            articlesRepository.addArticleToFavorites(slug = slug)
        }
    }
}