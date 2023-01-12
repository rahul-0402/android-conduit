package com.rahulghag.conduit.ui.articles.list

import com.rahulghag.conduit.common.UiMessage
import com.rahulghag.conduit.domain.models.Article

data class ArticleListUiState(
    val isLoading: Boolean = false,
    val message: UiMessage? = null,
    val articles: List<Article>? = emptyList(),
)
