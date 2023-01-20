package com.rahulghag.conduit.ui.articles.list

import com.rahulghag.conduit.domain.models.Article
import com.rahulghag.conduit.utils.UiMessage

data class ArticleListUiState(
    val articles: List<Article>? = emptyList(),
    val isLoading: Boolean = false,
    val message: UiMessage? = null
)
