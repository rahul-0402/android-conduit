package com.rahulghag.conduit.ui.articles.list

sealed class ArticleListUiEvent {
    object RefreshArticleList : ArticleListUiEvent()
}
