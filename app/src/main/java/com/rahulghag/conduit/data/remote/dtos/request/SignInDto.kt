package com.rahulghag.conduit.data.remote.dtos.request

import com.google.gson.annotations.SerializedName

data class SignInDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)