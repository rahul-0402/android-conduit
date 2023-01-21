package com.rahulghag.conduit.domain.repositories

import com.rahulghag.conduit.domain.models.Article
import com.rahulghag.conduit.utils.ArticleSortType
import com.rahulghag.conduit.utils.Resource

interface ArticlesRepository {
    suspend fun getArticles(articleSortType: ArticleSortType): Resource<List<Article>>

    suspend fun getArticle(slug: String): Resource<Article>

    suspend fun addArticleToFavorites(slug: String): Resource<Article>

    suspend fun removeArticleFromFavorites(slug: String): Resource<Article>

    suspend fun createArticle(title: String, description: String, body: String): Resource<Article>
}