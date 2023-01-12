package com.rahulghag.conduit.ui.articles.details

import com.rahulghag.conduit.common.UiMessage
import com.rahulghag.conduit.domain.models.Article

data class ArticleDetailsUiState(
    val isLoading: Boolean = false,
    val message: UiMessage? = null,
    val slug: String = "",
    val article: Article? = null
)