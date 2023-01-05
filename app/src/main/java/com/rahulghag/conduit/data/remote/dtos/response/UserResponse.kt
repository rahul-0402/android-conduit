package com.rahulghag.conduit.data.remote.dtos.response


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("user")
    val userDto: UserDto
)