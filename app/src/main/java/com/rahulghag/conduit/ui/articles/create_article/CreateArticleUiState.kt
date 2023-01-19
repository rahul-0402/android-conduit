package com.rahulghag.conduit.ui.articles.create_article

import com.rahulghag.conduit.utils.UiMessage

data class CreateArticleUiState(
    val title: String = "",
    val description: String = "",
    val articleBody: String = "",
    val isArticlePublished: Boolean = false,
    val isLoading: Boolean = false,
    val message: UiMessage? = null
)