package com.rahulghag.conduit.features.auth.data.remote.dtos.request


import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("user")
    val signIn: SignInDto
)