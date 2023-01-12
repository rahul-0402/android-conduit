package com.rahulghag.conduit.domain.repositories

import com.rahulghag.conduit.common.Resource
import com.rahulghag.conduit.domain.models.Article

interface ArticlesRepository {
    suspend fun getArticles(): Resource<List<Article>>

    suspend fun getArticle(slug: String): Resource<Article>
}