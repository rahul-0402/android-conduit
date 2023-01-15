package com.rahulghag.conduit.features.articles.ui.details

sealed class ArticleDetailsUiEvent {
    object ToggleFollowUser : ArticleDetailsUiEvent()
    object ToggleFavoriteArticle : ArticleDetailsUiEvent()
}