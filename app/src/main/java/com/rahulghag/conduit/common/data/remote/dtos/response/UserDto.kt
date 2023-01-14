package com.rahulghag.conduit.common.data.remote.dtos.response

import com.google.gson.annotations.SerializedName
import com.rahulghag.conduit.common.domain.models.User

data class UserDto(
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("email")
    val email: String,
    @SerializedName("image")
    val image: Any,
    @SerializedName("token")
    val token: String,
    @SerializedName("username")
    val username: String
) {
    fun toUser(): User {
        return User(
            bio = bio,
            email = email,
            image = image,
            token = token,
            username = username
        )
    }
}