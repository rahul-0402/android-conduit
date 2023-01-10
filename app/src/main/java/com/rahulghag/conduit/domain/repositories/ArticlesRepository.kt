package com.rahulghag.conduit.domain.repositories

import com.rahulghag.conduit.domain.models.Article
import com.rahulghag.conduit.utils.Resource

interface ArticlesRepository {
    suspend fun getArticles(): Resource<List<Article>>

    suspend fun getArticle(slug: String): Resource<Article>
}