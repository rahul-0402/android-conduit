package com.rahulghag.conduit.data.remote.dtos.response


import com.google.gson.annotations.SerializedName
import com.rahulghag.conduit.domain.models.Article

data class ArticleDto(
    @SerializedName("author")
    val author: AuthorDto,
    @SerializedName("body")
    val body: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("favorited")
    val isFavorite: Boolean,
    @SerializedName("favoritesCount")
    val favoritesCount: Int,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("tagList")
    val tags: List<String>,
    @SerializedName("title")
    val title: String,
    @SerializedName("updatedAt")
    val updatedAt: String
) {
    fun toArticle(): Article {
        return Article(
            author = author.toAuthor(),
            body = body,
            createdAt = createdAt,
            description = description,
            isFavorite = isFavorite,
            favoritesCount = favoritesCount,
            slug = slug,
            tags = tags,
            title = title,
            updatedAt = updatedAt
        )
    }
}