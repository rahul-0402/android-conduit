package com.rahulghag.conduit.ui.articles.details

sealed class ArticleDetailsUiEvent {
    object ToggleFollowUser : ArticleDetailsUiEvent()
    object ToggleFavoriteArticle : ArticleDetailsUiEvent()
}
