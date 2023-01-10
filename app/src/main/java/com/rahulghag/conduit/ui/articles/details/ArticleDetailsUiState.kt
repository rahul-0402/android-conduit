package com.rahulghag.conduit.ui.articles.details

import com.rahulghag.conduit.domain.models.Article
import com.rahulghag.conduit.utils.UiMessage

data class ArticleDetailsUiState(
    val isLoading: Boolean = false,
    val message: UiMessage? = null,
    val slug: String = "",
    val article: Article? = null
)