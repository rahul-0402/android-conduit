package com.rahulghag.conduit.common.data.remote.dtos.response


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("user")
    val userDto: UserDto
)