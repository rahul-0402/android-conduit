package com.rahulghag.conduit.features.articles.ui.details

import com.rahulghag.conduit.common.utils.UiMessage

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