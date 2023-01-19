package com.rahulghag.conduit.domain.models

data class User(
    val bio: String?,
    val email: String,
    val image: Any,
    val token: String,
    val username: String
)