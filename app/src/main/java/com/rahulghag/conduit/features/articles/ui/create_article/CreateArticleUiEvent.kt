package com.rahulghag.conduit.features.articles.ui.create_article

sealed class CreateArticleUiEvent {
    data class TitleChanged(val title: String) : CreateArticleUiEvent()
    data class DescriptionChanged(val description: String) : CreateArticleUiEvent()
    data class ArticleBodyChanged(val articleBody: String) : CreateArticleUiEvent()
    object PublishArticle : CreateArticleUiEvent()
}
