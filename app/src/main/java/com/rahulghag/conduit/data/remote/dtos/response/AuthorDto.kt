package com.rahulghag.conduit.data.remote.dtos.response


import com.google.gson.annotations.SerializedName
import com.rahulghag.conduit.domain.models.Author

data class AuthorDto(
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("following")
    val isFollowing: Boolean,
    @SerializedName("image")
    val image: String,
    @SerializedName("username")
    val username: String
) {
    fun toAuthor(): Author {
        return Author(
            bio = bio,
            isFollowing = isFollowing,
            username = username
        )
    }
}