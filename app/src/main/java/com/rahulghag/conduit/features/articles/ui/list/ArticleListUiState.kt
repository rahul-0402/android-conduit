package com.rahulghag.conduit.features.articles.ui.list

import com.rahulghag.conduit.common.utils.UiMessage
import com.rahulghag.conduit.features.articles.domain.models.Article

data class ArticleListUiState(
    val isLoading: Boolean = false,
    val message: UiMessage? = null,
    val articles: List<Article>? = emptyList(),
)
