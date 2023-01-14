package com.rahulghag.conduit.features.articles.ui.details

import com.rahulghag.conduit.common.utils.UiMessage
import com.rahulghag.conduit.features.articles.domain.models.Article

data class ArticleDetailsUiState(
    val slug: String = "",
    val article: Article? = null,
    val isLoading: Boolean = false,
    val message: UiMessage? = null
)