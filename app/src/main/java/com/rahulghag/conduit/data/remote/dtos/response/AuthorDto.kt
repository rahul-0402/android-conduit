package com.rahulghag.conduit.data.remote.dtos.response


import com.google.gson.annotations.SerializedName

data class AuthorDto(
    @SerializedName("bio")
    val bio: String,
    @SerializedName("following")
    val isFollowing: Boolean,
    @SerializedName("image")
    val image: String,
    @SerializedName("username")
    val username: String
)