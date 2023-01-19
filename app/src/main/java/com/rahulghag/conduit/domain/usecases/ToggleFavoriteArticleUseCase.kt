package com.rahulghag.conduit.domain.usecases

import com.rahulghag.conduit.domain.models.Article
import com.rahulghag.conduit.domain.repositories.ArticlesRepository
import com.rahulghag.conduit.utils.Resource

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