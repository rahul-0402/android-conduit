package com.rahulghag.conduit.features.articles.ui.details

import com.rahulghag.conduit.common.utils.UiMessage

data class ArticleDetailsUiState(
    val title: String = "",
    val body: String = "",
    val publishedDate: String = "",
    val authorName: String = "",
    val isFollowingAuthor: Boolean? = null,
    val isLoading: Boolean = false,
    val message: UiMessage? = null
)