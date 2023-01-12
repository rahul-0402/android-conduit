package com.rahulghag.conduit.ui.articles.details

sealed class ArticleDetailsUiEvent {
    object FollowAuthor : ArticleDetailsUiEvent()
    object UnfollowAuthor : ArticleDetailsUiEvent()
}
