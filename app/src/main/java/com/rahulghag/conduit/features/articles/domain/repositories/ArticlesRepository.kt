package com.rahulghag.conduit.features.articles.domain.repositories

import com.rahulghag.conduit.common.utils.Resource
import com.rahulghag.conduit.features.articles.domain.models.Article

interface ArticlesRepository {
    suspend fun getArticles(): Resource<List<Article>>

    suspend fun getArticle(slug: String): Resource<Article>

    suspend fun addArticleToFavorites(slug: String): Resource<Article>

    suspend fun removeArticleFromFavorites(slug: String): Resource<Article>
}