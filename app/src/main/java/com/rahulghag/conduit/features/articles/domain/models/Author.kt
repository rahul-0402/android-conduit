package com.rahulghag.conduit.features.articles.domain.models

data class Author(
    val bio: String?,
    val isFollowing: Boolean,
    val username: String
)