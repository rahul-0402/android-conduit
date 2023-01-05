package com.rahulghag.conduit.data.remote.dtos.request


import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("user")
    val signIn: SignInDto
)