package com.rahulghag.conduit.features.articles.domain.models

data class Article(
    val author: Author,
    val body: String,
    val createdAt: String,
    val description: String,
    val isFavorite: Boolean,
    val favoritesCount: Int,
    val slug: String,
    val tags: List<String>,
    val title: String,
    val updatedAt: String
)