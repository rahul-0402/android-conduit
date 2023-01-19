package com.rahulghag.conduit.data.remote.dtos.response


import com.google.gson.annotations.SerializedName
import com.rahulghag.conduit.domain.models.Profile

data class ProfileDto(
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("following")
    val following: Boolean,
    @SerializedName("image")
    val image: String,
    @SerializedName("username")
    val username: String
) {
    fun toProfile(): Profile {
        return Profile(
            bio = bio ?: "",
            isFollowing = following,
            username = username
        )
    }
}