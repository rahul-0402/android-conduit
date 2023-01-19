package com.rahulghag.conduit.ui.articles.details

import com.rahulghag.conduit.utils.UiMessage

data class ArticleDetailsUiState(
    val authorName: String = "",
    val isFollowingAuthor: Boolean? = null,
    val publishedDate: String = "",
    val title: String = "",
    val body: String = "",
    val isFavorite: Boolean? = null,
    val isLoading: Boolean = false,
    val message: UiMessage? = null
)