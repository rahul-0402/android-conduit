package com.rahulghag.conduit.features.articles.ui.list

sealed class ArticleListUiEvent {
    object RefreshArticleList : ArticleListUiEvent()
}
