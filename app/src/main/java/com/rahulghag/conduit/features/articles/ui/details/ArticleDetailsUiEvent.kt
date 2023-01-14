package com.rahulghag.conduit.features.articles.ui.details

sealed class ArticleDetailsUiEvent {
    object FollowAuthor : ArticleDetailsUiEvent()
    object UnfollowAuthor : ArticleDetailsUiEvent()
}
