package com.rahulghag.conduit.features.articles.ui.create_article

import com.rahulghag.conduit.common.utils.UiMessage

data class CreateArticleUiState(
    val title: String = "",
    val description: String = "",
    val articleBody: String = "",
    val isArticlePublished: Boolean = false,
    val isLoading: Boolean = false,
    val message: UiMessage? = null
)