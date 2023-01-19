package com.rahulghag.conduit.domain.models

data class Profile(
    val bio: String,
    val isFollowing: Boolean,
    val username: String
)